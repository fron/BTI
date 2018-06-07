package com.example.francisqueins.breakingtheice;

/**
 * Created by Francisqueins on 6/23/2017.
 * This program allows a user to create an
 * I.C.E. alert
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class sendAmessage extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final DBmanager myDbManager = new DBmanager(sendAmessage.this); // DB not created at this point

        Cursor c=null;
        String[] numbersArrayList = new String[100]; //Up to 100 contacts
        Integer numContacts = -1;

        try {
            myDbManager.openDataBase();
        }
        catch(SQLException sqle) {
            throw sqle;
        }
        c=myDbManager.query("contacts", null, null, null, null,null, null); // Select table locations from DB
        if(c.moveToFirst()) do {

            numContacts++;
            numbersArrayList[numContacts] = (c.getString(1));

        } while (c.moveToNext());

        String toNumber;

        // this is to check the SEND MESSAGE permission
        // if there's no permission it will ask
        // if the user allows it, it'll be permanently for the app

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
               != PackageManager.PERMISSION_GRANTED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.SEND_SMS};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            }
        }
        // here I have to create a new alert on the database


        for ( int i=0; i<= numContacts; i++)
        {
            toNumber = numbersArrayList[i];
            sendSMS(toNumber, "This is an auto generated message -DO NOT REPLY- there is a I.C.E. raid " +
                    "in progress near you. Further details at the 'Breaking The I.C.E.' application.");
        }

        if(numContacts >= 0){
            Toast.makeText(getApplicationContext(), "Message sent to your contacts", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //Sends an SMS message to another device
    private void sendSMS(String phoneNumber, String message) {

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
