package com.example.francisqueins.breakingtheice;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static android.widget.Toast.LENGTH_SHORT;
import static com.example.francisqueins.breakingtheice.R.id.buttonCancel;
import static com.example.francisqueins.breakingtheice.R.id.buttonSave;
import static com.example.francisqueins.breakingtheice.R.id.phoneView;
import static com.example.francisqueins.breakingtheice.R.id.text;

public class registerFull extends AppCompatActivity {

    Button bSave;
    Button bCancel;
    public String usrPhoneM;
    public String usrCodeM;
    public TextView phoneV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_full);

        usrCodeM = getCode();
        usrPhoneM = getPhoneNumber();

        phoneV = (TextView) findViewById(R.id.phoneView);
        phoneV.setText(usrPhoneM); // publishing phone number

        bSave = (Button) findViewById(buttonSave);
        bCancel = (Button) findViewById(buttonCancel);

        // Changing the fonts for the buttons
        bSave.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Light.ttf"));
        bCancel.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Light.ttf"));
    }

    // ON SAVE for button "Save"
    public void onSave(View view) {

        EditText usrName, usrPwd, usrCode;

        usrName = (EditText) findViewById(R.id.textName);
        usrPwd = (EditText) findViewById(R.id.textPwd);
        usrCode = (EditText) findViewById(R.id.textCode);

        String usrNameA = usrName.getText().toString();
        String usrPwdA = usrPwd.getText().toString();
        String usrCodeA = usrCode.getText().toString();
        // Date: no special characters for it
        String usrCreatedA = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String usrLastLoginA = "0";
        String usrAddAlertDate = "0";
        String usrAddAlertTime = "0";
        String usrActive = "A";
        String type = "register"; // this is the operation we want to execute

        // Validating data
        boolean isValid = true;

        // name is not blank
        if (usrNameA.equals(" ")) {

            isValid = false;

        // pwd is present
        } else if (usrPwdA.equals(" ")) {

            isValid = false;

        // code must be the same sent in a msg
        } else if (!usrCodeA.equals(usrCodeM)) {

            isValid = false;
        }

        if(isValid){
            //Toast.makeText(registerFull.this, "Tutto bene", LENGTH_SHORT).show();
            // **** executing the async task ************************************
            myAsyncTask MyasyncTask = new myAsyncTask(this);
            MyasyncTask.execute(type, usrPhoneM, usrNameA, usrPwdA, usrCreatedA, usrLastLoginA, usrAddAlertDate, usrAddAlertTime, usrActive);
            finish();

        } else {

            Toast.makeText(registerFull.this, "Problems", LENGTH_SHORT).show();
        }
    }

     public void OnCancel (View view) {
         Context context = getApplicationContext();
         finish();
     }

    //Sends an SMS message to another device
    private void sendSMS(String phoneNumber, String message) {

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

    public String getPhoneNumber () {
        Intent i= getIntent();
        String phoneNumber = i.getExtras().getString("usrPhone");
        return phoneNumber;
    }

    public String getCode () {
        Intent i= getIntent();
        String code = i.getExtras().getString("usrCode");
        return code;
    }

}