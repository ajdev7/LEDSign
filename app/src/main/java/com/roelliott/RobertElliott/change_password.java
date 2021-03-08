package com.roelliott.RobertElliott;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;

/**
 * Created by roelliott on 7/15/2016.
 */
public class change_password extends Activity implements View.OnClickListener {

    private Button save_button;
    private EditText myTextUserName, myTextPassword, myTextPasswordAgain;
    String pwstring2 = "", unstring ="", pwstring1 = "";
    FileOutputStream outputStream;
    String PW = "Password_file";
    String UN = "User_name_file";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        myTextUserName = (EditText) findViewById(R.id.etUser_name);
        myTextPassword = (EditText) findViewById(R.id.etPassword);
        myTextPasswordAgain = (EditText) findViewById(R.id.etPassword_again);
        save_button = (Button) findViewById(R.id.bSave_and_finish);
        save_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        boolean success = false;
        switch (view.getId()) {
            case R.id.bSave_and_finish:
                unstring =  myTextUserName.getText().toString();
                pwstring1 = myTextPassword.getText().toString();
                pwstring2 = myTextPasswordAgain.getText().toString();
                /*Toast.makeText(this, "the password from the user name et box = " + unstring, Toast.LENGTH_LONG).show();
                Toast.makeText(this, "the password from the password et box = " + pwstring1, Toast.LENGTH_LONG).show();
                Toast.makeText(this, "the password from the password again et box = " + pwstring2, Toast.LENGTH_LONG).show();*/

                if (pwstring1.equals(pwstring2))
                {
                    try {
                        outputStream = openFileOutput(PW, Context.MODE_PRIVATE);
                        outputStream.write(pwstring1.getBytes());
                        outputStream.close();
                        outputStream = openFileOutput(UN, Context.MODE_PRIVATE);
                        outputStream.write(unstring.getBytes());
                        outputStream.close();
                        success = true;

                    } catch (Exception e) {
                        e.printStackTrace();
                        //success = false;
                    } finally {
                        if (success == true) {
                            Toast.makeText(this, "Password has been changed", Toast.LENGTH_LONG).show();
                            Intent MyIntent = new Intent(view.getContext(), DeviceScanActivity.class);
                            startActivityForResult(MyIntent, 0);
                        } else {
                            Toast.makeText(this, "Password failed; check errors" , Toast.LENGTH_LONG).show();
                            Intent MyIntent = new Intent(view.getContext(), PasswordActivity.class);
                            startActivityForResult(MyIntent, 0);
                        }
                    }
                } else
                    Toast.makeText(this, "Passwords do not match; try again", Toast.LENGTH_LONG).show();
        }

    }
}