package com.example.francisqueins.breakingtheice;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

import static android.widget.Toast.LENGTH_SHORT;
import static com.example.francisqueins.breakingtheice.R.id.buttonCancel;
import static com.example.francisqueins.breakingtheice.R.id.buttonSave;

public class registerPhone extends AppCompatActivity {

    Button bSave;
    Button bCancel;
    private static final int PERMISSION_REQUEST_CODE = 1;
    public String randomN;
    public boolean isValid = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);

        bSave = (Button) findViewById(buttonSave);
        bCancel = (Button) findViewById(buttonCancel);


        // Changing the fonts for the buttons
        bSave.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Light.ttf"));
        bCancel.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Light.ttf"));
    }

    // ON SAVE for button "Save"
    public void onSave(View view) {

        EditText usrPhone;
        usrPhone = (EditText) findViewById(R.id.textPhone);
        String usrPhoneA = usrPhone.getText().toString();

        // phone has been entered
        if (usrPhoneA == " ") {

            isValid = false;

            // name is present
        } else {
            Random rand = new Random();
            int n = (rand.nextInt(9999 - 1000) + 1000); // Gives n such that 1000 <= n < 9999
            randomN = Integer.toString(n);
            // this is to check the SEND MESSAGE permission
            // if there's no permission it will ask
            // if the user allows it, it'll be permanently for the app
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {

                    Log.d("permission", "permission denied to SEND_SMS - requesting it");
                    String[] permissions = {android.Manifest.permission.SEND_SMS};

                    requestPermissions(permissions, PERMISSION_REQUEST_CODE);
                }
            }

            sendSMS(usrPhoneA, "Your code for BTI is" + " " + randomN);
            Toast.makeText(getApplicationContext(), "A 4-digit code has been sent to your phone . . .", Toast.LENGTH_LONG).show();
            // calling register for additional info.
            Intent  theIntent = new Intent(getApplication(), registerFull.class);
            theIntent.putExtra("usrPhone", usrPhoneA); // phone
            theIntent.putExtra("usrCode", randomN);    // generates code for security
            startActivity(theIntent);

            finish(); // method no longer needed
        }
    }

     public void onCancel(View view) {
         Context context = getApplicationContext();
         finish();
     }

    //Sends an SMS message to another device
    private void sendSMS(String phoneNumber, String message) {

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

}