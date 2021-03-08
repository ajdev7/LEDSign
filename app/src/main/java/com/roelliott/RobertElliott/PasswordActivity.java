package com.roelliott.RobertElliott;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by roelliott on 7/13/2016.
 */
public class PasswordActivity extends Activity {
    CheckBox cb;
    Button b1, b2;
    EditText ed1, ed2;
    String emptyString = "";
    String PW = "Password_file";
    String UN = "User_name_file";
    String IF = "Init_file";
    String Init = "";
    String User_name = "roelliott";
    String Password = "christy1";
    String User_name_returned = "";
    String Password_returned = "";
    String Initializtion_returned = "";
    String Empty_string = "";
    String myUNstring, myPWstring, myIFstring;
    private String ExitAll = "FALSE";
    final int REQUEST_CODE = 1;
    FileOutputStream outputStream;
    FileInputStream inputStream;

    String init = "", currentValue, temp = "";
    //String EXTRAS_ENDALL;
    byte[] myBytes;
    String myString = "";
    char myChar;
    TextView tx1;
    int counter = 3;
    int data;
    boolean myReturnedBool;
    boolean success = true;
    boolean isFileCreated = false;
    String checkCreation = "";
    String TF, MYSTRING;
    String Uname, Pword;

    public void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.password_activity);
        b1 = (Button) findViewById(R.id.bLogin);
        ed1 = (EditText) findViewById(R.id.etEnter_Name);
        ed2 = (EditText) findViewById(R.id.etPassword);
        cb = (CheckBox) findViewById(R.id.cbChange_Password);
        b2 = (Button) findViewById(R.id.bCancel);
        tx1 = (TextView) findViewById(R.id.tvNEW);
        tx1.setVisibility(View.GONE);
        super.onCreate(savedInstanceState);

        Intent MyIntent;
        MyIntent = getIntent();

        myReturnedBool = MyIntent.getBooleanExtra("EXTRAS_ENDALL", false );
        /*if (myReturnedBool == true)
            Toast.makeText(this, "returned boolean is true", Toast.LENGTH_LONG).show();
        else if (myReturnedBool == false)
            Toast.makeText(this, "returned boolean is false", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "returned boolean is neither true or false", Toast.LENGTH_LONG).show();*/
        if (myReturnedBool == true)
            finish();



        try {
            File init_file = new File(getFilesDir() + "/" + IF);
            if (!init_file.exists()) {
                isFileCreated = init_file.createNewFile();
            }
            if (isFileCreated) {
                Writer writer = new FileWriter(init_file);
                writer.flush();
                writer.write(Empty_string);
                writer.close();
                //Toast.makeText(this, "FILE WAS CREATED", Toast.LENGTH_LONG).show();
            } /*else {
                Toast.makeText(this, "FILE ALREADY EXISTS", Toast.LENGTH_LONG).show();
            }*/
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "FILE WAS NOT CREATED", Toast.LENGTH_LONG).show();
        }
        try {
            inputStream = openFileInput(IF);//
            data = inputStream.read();
            while (data != -1) {
                init = init + (char) data;
                data = inputStream.read();
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Toast.makeText(this, "(I am just before the initialization module) and the init file contents = " + init, Toast.LENGTH_LONG).show();
        if (!init.equals("true")) {
            init = "true";
            try {
                File init_file_out = new File(getFilesDir() + "/" + IF);
                isFileCreated = init_file_out.createNewFile();
                outputStream = openFileOutput(IF, Context.MODE_PRIVATE);
                outputStream.write(init.getBytes());
                outputStream.close();
                inputStream = openFileInput(IF);
                TF = "";
                data = inputStream.read();
                while (data != -1) {
                    TF = TF + (char) data;
                    data = inputStream.read();
                }
                inputStream.close();
                //Toast.makeText(this, "(I am in the initialization module) and the init file contents = " + TF, Toast.LENGTH_LONG).show();
                File user_name_file = new File(getFilesDir() + "/" + UN);
                isFileCreated = user_name_file.createNewFile();
                outputStream = openFileOutput(UN, Context.MODE_PRIVATE);
                outputStream.write(User_name.getBytes());
                outputStream.close();
                File password_file = new File(getFilesDir() + "/" + PW);
                isFileCreated = password_file.createNewFile();
                outputStream = openFileOutput(PW, Context.MODE_PRIVATE);
                outputStream.write(Password.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "I am in the initialization module exception", Toast.LENGTH_LONG).show();
            }
        }

        try {
            User_name_returned = "";
            inputStream = openFileInput(UN);
            data = inputStream.read();
            while (data != -1) {
                User_name_returned = User_name_returned + (char) data;
                data = inputStream.read();
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Password_returned = "";
            inputStream = openFileInput(PW);
            data = inputStream.read();
            while (data != -1) {
                Password_returned = Password_returned + (char) data;
                data = inputStream.read();
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed1.getText().toString().equals(User_name_returned) &&

                        ed2.getText().toString().equals(Password_returned)) {
                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                    if (!cb.isChecked()) {
                        Intent MyIntent = new Intent(v.getContext(), DeviceScanActivity.class);
                        startActivity(MyIntent);
                    } else {
                        Intent MyIntent1 = new Intent(v.getContext(), change_password.class);
                        startActivity(MyIntent1);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                    tx1.setVisibility(View.VISIBLE);
                    tx1.setBackgroundColor(Color.RED);
                    counter--;
                    tx1.setText(Integer.toString(counter));

                    if (counter == 0) {
                        b1.setEnabled(false);
                    }
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    //Intent sendingIntent = getIntent();
    // = sendingIntent.getStringExtra("EXTRAS_ENDALL");


}


