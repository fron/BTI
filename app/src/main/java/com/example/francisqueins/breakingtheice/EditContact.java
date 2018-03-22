package com.example.francisqueins.breakingtheice;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;
import java.util.HashMap;

public class EditContact extends AppCompatActivity {

        EditText conPhone;
        EditText conName;

        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_contacts);
            final DBmanager myDbManager = new DBmanager(EditContact.this); // we can use DBmanager methods
            // Button for updating a, also changing the font
            Button updateButton = (Button)findViewById(R.id.updateButton);
            updateButton.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Light.ttf"));

            // Button for deleting a contact, also changing the font
            Button deleteButton = (Button)findViewById(R.id.deleteButton);
            deleteButton.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Light.ttf"));

            conPhone = (EditText) findViewById(R.id.conPhone);
            conName  = (EditText) findViewById(R.id.conName);

            Intent i = getIntent();
            String _conPhone = i.getStringExtra("conPhone");  // we look for the phone selected in the list
            HashMap<String, String> contactInfo = myDbManager.getContactInfo(_conPhone);

            // Loading location info
            if(contactInfo.size() != 0){
                conPhone.setText(contactInfo.get("conPhone"));
                conName.setText(contactInfo.get("conName"));
            }
        }

        public void updateContact(View view) throws SQLException {

            HashMap<String, String> queryValues = new HashMap<String, String>();
            conPhone = (EditText) findViewById(R.id.conPhone);
            conName = (EditText) findViewById(R.id.conName);

            Intent i = getIntent();
            queryValues.put("_Id", i.getStringExtra("_Id"));   // update is based on auto generated key, phone number may be updated as well
            queryValues.put("conPhone", conPhone.getText().toString());
            queryValues.put("conName", conName.getText().toString());

            final DBmanager myDbManager = new DBmanager(EditContact.this); // update location
            myDbManager.updateContact(queryValues);
            this.callMainActivity(view);
            finish();
        }

        // ON CLICK "Delete" button
        public void removeContact(View view) throws SQLException {
            Intent i = getIntent();
            String _Id = i.getStringExtra("_Id");
            final DBmanager myDbManager = new DBmanager(EditContact.this);
            myDbManager.deleteContact(_Id);
            this.callMainActivity(view);
            finish();
        }

        // User decided to go back (no data modified)
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event)  {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                View view = null;
                this.callMainActivity(view);
                finish();
            }
            return super.onKeyDown(keyCode, event);
        }

        // Main process (Locations list)
        public void callMainActivity(View view) {
            Intent objIntent = new Intent(getApplication(), manageContacts.class);
            startActivity(objIntent);
        }
}
