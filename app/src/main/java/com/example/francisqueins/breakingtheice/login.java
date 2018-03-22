package com.example.francisqueins.breakingtheice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.concurrent.ExecutionException;

import static android.widget.Toast.LENGTH_SHORT;
import static com.example.francisqueins.breakingtheice.R.id.buttonCancel;
import static com.example.francisqueins.breakingtheice.R.id.buttonLogin;
import static com.example.francisqueins.breakingtheice.R.id.buttonRegister;

public class login extends AppCompatActivity {

    Button bLogin;
    Button bCancel;
    Button bRegister;

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bLogin = (Button) findViewById(buttonLogin);
        bCancel = (Button) findViewById(buttonCancel);
        bRegister = (Button) findViewById(buttonRegister);

        // Changing the fonts for the buttons
        bLogin.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Light.ttf"));
        bCancel.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Light.ttf"));
        bRegister.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Light.ttf"));

    }

    // ON CLICK for button "login"
    public void onLogin(View view) throws ExecutionException, InterruptedException {

        EditText usrPhone, usrPWD;
        usrPhone = (EditText) findViewById(R.id.textPhone);
        usrPWD = (EditText) findViewById(R.id.textPWD);
        String usrPhoneA = usrPhone.getText().toString();
        String usrPWDA = usrPWD.getText().toString();
        String type = "login"; // this is the operation we want to execute

       // **** executing the async task ************************************
        myAsyncTask MyasyncTask = new myAsyncTask(this);
        String result = MyasyncTask.execute(type, usrPhoneA, usrPWDA).get();

      // *******************************************************************

        // login successful
        if (result.equals("Welcome")) {

            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("usrPhone", String.valueOf(usrPhoneA));
            editor.apply();

            Intent theIntent = new Intent(getApplication(), MainActivity.class);
            startActivity(theIntent); // display map with user location and destination for tracking

            // wrong usr or pwd
        } else if (result.equals("Incorrect phone # or pwd")) {

            Toast.makeText(login.this, "User or Password incorrect", LENGTH_SHORT).show();

        }

    }

     public void onCancel(View view) {
         Context context = getApplicationContext();
         finish();
     }

    public void onRegister(View view) {
        Intent  theIntent = new Intent(getApplication(), registerPhone.class);
        startActivity(theIntent); // create new usr

    }

}