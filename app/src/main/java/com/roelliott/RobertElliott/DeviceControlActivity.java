/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.roelliott.RobertElliott;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//import android.OnClickListener;
//import android.widget.SeekBar;

public class DeviceControlActivity extends Activity implements View.OnClickListener {

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    private final static String TAG = DeviceControlActivity.class.getSimpleName();
    private static final String MLDP_PRIVATE_SERVICE = "00035b03-58e6-07dd-021a-08123a000300"; //Private service for Microchip MLDP
    private static final String MLDP_DATA_PRIVATE_CHAR = "00035b03-58e6-07dd-021a-08123a000301"; //Characteristic for MLDP Data, properties - notify, write
    private static final String MLDP_CONTROL_PRIVATE_CHAR = "00035b03-58e6-07dd-021a-08123a0003ff"; //Characteristic for MLDP Control, properties - read, write
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private EditText putText;
    private TextView mConnectionState;
    private Button sendText, getTextFromSpeech, exitAll;
    private String mDeviceAddress;
    private BluetoothLeService mBluetoothLeService;
    private String sending, string, myString;

    // ----------------------------------------------------------------------------------------------------------------
    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {        //Create new ServiceConnection interface to handle connection and disconnection

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {        //Service connects
            //mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();    //Get a link to the service
            if (!mBluetoothLeService.initialize()) {                        //See if the service did not initialize properly
                Log.e(TAG, "Unable to initialize Bluetooth");                    //Send debug message
                finish();                                                                    //End the application
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);                    //Service connects to the device selected and passed to us by the DeviceScanActivity
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {            //Service disconnects
            mBluetoothLeService = null;                                //Service has no connection
        }
    };
    private BluetoothGattCharacteristic mDataMDLP, mControlMLDP;
    private boolean mConnected = false;
    // ----------------------------------------------------------------------------------------------------------------
    // BroadcastReceiver handles various events fired by the BluetoothLeService service.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {            //Service has connected to BLE device
                mConnected = true;
                updateConnectionState(R.string.connected);
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {        //Service has disconnected from BLE device
                mConnected = false;
                updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
                //ledSeekBar.setProgress(0);
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {    //Service has discovered GATT services on BLE device
                findMldpGattService(mBluetoothLeService.getSupportedGattServices());        //Show all the supported services and characteristics on the user interface
            } else if (BluetoothLeService.ACTION_DATA_WRITTEN.equals(action)) {            //Service has connected to BLE device
                Log.d(TAG, "Sending: chuck done");
                sendChunk();
            }
        }
    };

    // ----------------------------------------------------------------------------------------------------------------
    // Intent filter to add Intent values that will be broadcast by the BluetoothLeService to the mGattUpdateReceiver BroadcastReceiver
    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_WRITTEN);
        return intentFilter;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // Activity launched
    // Invoked by Intent in onListItemClick method in DeviceScanActivity
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explorer_control);
        int x = 0;
        final Intent intent = getIntent();                                        //Get the Intent that launched this activity
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);            //Get the BLE device address from the Intent

        // Sets up UI references.
        ((TextView) findViewById(R.id.deviceAddress)).setText(mDeviceAddress);    //Display device address on the screen
        mConnectionState = (TextView) findViewById(R.id.connectionState);        //Get a reference to the TextView that will display the connection state
        getTextFromSpeech = (Button) findViewById(R.id.bTextFromSpeech);
        sendText = (Button) findViewById(R.id.bSendText);
        exitAll = (Button) findViewById(R.id.bfinishAll);
        putText = (EditText) findViewById(R.id.etPutText);

        exitAll.setOnClickListener(this);
        sendText.setOnClickListener(this);
        getTextFromSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }

        });
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);    //Create Intent to start the BluetoothLeService
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);    //Create and bind the new service to mServiceConnection object that handles service connect and disconnect
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    putText.setText(result.get(0));
                }
                break;
            }

        }
    }

    // ----------------------------------------------------------------------------------------------------------------
    // Activity resumed
    // Register the GATT receiver
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());    //Register receiver to handles events fired by the service: connected, disconnected, discovered services, received data from read or notification operation
        if (mBluetoothLeService != null) {                                        //Is there already a service (probably not yet)
            final boolean result = mBluetoothLeService.connect(mDeviceAddress); //Ask the service to connect to the GATT server hosted on the Bluetooth LE device
            Log.d(TAG, "Connect request result = " + result);
        }
    }

    // ----------------------------------------------------------------------------------------------------------------
    // Activity paused
    // Unregister the GATT receiver
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    // ----------------------------------------------------------------------------------------------------------------
    // Activity is ending
    //
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // Options menu is different depending on whether connected or not
    // Show Connect option if not connected or show Disconnect option if we are connected
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gatt_services, menu);
        if (mConnected) {
            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);
        } else {
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
        }
        return true;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // Menu item selected
    // Connect or disconnect to BLE device
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_connect:
                mBluetoothLeService.connect(mDeviceAddress);
                return true;
            case R.id.menu_disconnect:
                mBluetoothLeService.disconnect();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ----------------------------------------------------------------------------------------------------------------
    //
    private void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnectionState.setText(resourceId);
            }
        });
    }

    // Iterate through the supported GATT Services/Characteristics to see if the MLDP srevice is supported
    private void findMldpGattService(List<BluetoothGattService> gattServices) {
        if (gattServices == null) {
            Log.d(TAG, "findMldpGattService found no Services");
            return;
        }
        String uuid;
        mDataMDLP = null;
        // Loops through available GATT Services
        for (BluetoothGattService gattService : gattServices) {
            uuid = gattService.getUuid().toString();
            if (uuid.equals(MLDP_PRIVATE_SERVICE)) {
                List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                // Loops through available Characteristics.
                for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                    uuid = gattCharacteristic.getUuid().toString();
                    if (uuid.equals(MLDP_DATA_PRIVATE_CHAR)) {
                        mDataMDLP = gattCharacteristic;
                        Log.d(TAG, "findMldpGattService found MLDP data characteristics");
                    } else if (uuid.equals(MLDP_CONTROL_PRIVATE_CHAR)) {
                        mControlMLDP = gattCharacteristic;
                        //Toast.makeText(this, gattCharacteristic.getProperties(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "findMldpGattService found MLDP control characteristics");
                    }
                }
                break;
            }
        }
        if (mDataMDLP == null) {
            Toast.makeText(this, R.string.mldp_not_supported, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "findMldpGattService found no MLDP service");
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bSendText:
                if (mDataMDLP == null) {
                    Toast.makeText(this, R.string.mldp_not_supported, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "found no MLDP service");
                    return;
                }
                string = putText.getText().toString();
                Log.d(TAG, "Text to send before trimming: " + string);
                myString = "**";
                myString.concat(string);
                //string = myString;                //Toast.makeText( this, "The new string = " +  string + "is here", Toast.LENGTH_LONG).show();
                int trim = 234;
                //Toast.makeText( this, "This is the new concatenated string = " +  string, Toast.LENGTH_LONG).show();
                if (myString.length() > trim ) {
                    myString = myString.substring(0, trim);
                    Toast.makeText(this, "Message length cannot exceed " +  trim + " characters, your message will be abbreviated", Toast.LENGTH_LONG).show();
                }
                myString = myString + '#';
                Log.d(TAG, "Text to send: " + myString);
                sending = myString;
                sendChunk();
                break;

            case R.id.bfinishAll:
                Intent MyIntent = new Intent(view.getContext(),
                        PasswordActivity.class);
                MyIntent.putExtra("EXTRAS_ENDALL", true  );//Create Intent to start the DeviceControlActivity and Add BLE device name to the intent
                MyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(MyIntent);
                finish();

            default:
                break;
        }
    }

    public void sendChunk() {
        if (sending.length() > 0) {
            int count = sending.length() > 20 ? 20 : sending.length();
            String substring = sending.substring(0, count);
            sending = sending.substring(count);

            Log.d(TAG, "Sending: " + substring);
            mDataMDLP.setValue(substring);
            mBluetoothLeService.writeCharacteristic(mDataMDLP);
        } else {
            Log.d(TAG, "Sending: no more chunks");
        }
    }
}