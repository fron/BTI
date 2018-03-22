package com.example.francisqueins.breakingtheice;

import android.content.Intent;
import android.database.SQLException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

// Adding a new contact
// these contacts will receive a message
// from user about an alert (ICE)
public class NewContact extends AppCompatActivity {

    EditText conPhone;
    EditText conName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        // Button for saving contact, also changing the font
        Button addButton = (Button)findViewById(R.id.addButton);
        addButton.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Light.ttf"));

        // empty attributes to be filled
        conPhone = (EditText) findViewById(R.id.conPhone);
        conName = (EditText) findViewById(R.id.conName);
    }

    // ON CLICK for button "Save"
    public void addNewContact(View view) {
        HashMap<String, String> queryValuesMap =  new  HashMap<String, String>();
        queryValuesMap.put("conPhone", conPhone.getText().toString());
        queryValuesMap.put("conName", conName.getText().toString());

        final DBmanager myDbManager = new DBmanager(NewContact.this);
        try {
            myDbManager.insertContact(queryValuesMap);
        }
        catch(SQLException sqle){
            throw sqle;
        }
        this.callManageContacts(view);
        finish();
    }

       // Back to list of locations
    public void callManageContacts(View view) {
        Intent theIntent = new Intent(getApplicationContext(), manageContacts.class);
        startActivity(theIntent);
    }
}
