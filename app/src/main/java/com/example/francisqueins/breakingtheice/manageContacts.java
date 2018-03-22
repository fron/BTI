package com.example.francisqueins.breakingtheice;

import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class manageContacts extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

    final DBmanager myDbManager = new DBmanager(manageContacts.this); // DB not created at this point

    try {
        myDbManager.createDataBase();
    }
    catch (IOException ioe) {
        throw new Error("Unable to instantiate database");
    }

    try {
        myDbManager.openDataBase();
    }
    catch(SQLException sqle){
        throw sqle;
    }


    super.onCreate(savedInstanceState);
    // populate list and display it

    setContentView(R.layout.activity_contacts);

    // Change the default font for button1
    Button button1 = (Button)findViewById(R.id.button1);
    button1.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Light.ttf"));

    Cursor c=null;

    try {
         myDbManager.openDataBase();
    }
    catch(SQLException sqle) {
        throw sqle;
    }

    List<String> List = new ArrayList<String>(); // list to load view
    ListView lv;                                 // instance of listView
    ArrayAdapter<String> arrayAdapter;           // array interface to load view

    c=myDbManager.query("contacts", null, null, null, null,null, null); // Select table locations from DB
    if(c.moveToFirst()) {

        do {
            List.add(c.getString(1) + "       " + c.getString(2) + " "); // loading the array with phone and name
        } while (c.moveToNext());

        lv = (ListView)findViewById(R.id.listView1);  // find listView1 in activity_main.xml
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_row, List); // row contains format for locations
        lv.setAdapter(arrayAdapter); // display list

        lv.setOnItemClickListener(new OnItemClickListener() {

            // ON CLICK display the tracking map
            public void onItemClick(AdapterView<?> parent, View view,int pos_clicked, long row_clicked) {

                String val =(String) parent.getItemAtPosition(pos_clicked); // get the whole text selected
                String s[] = val.split(" "); // separates phone and name
                HashMap<String, String> contactsList = new HashMap<String, String>();
                contactsList = myDbManager.getContactInfo(s[0]);
                if(contactsList != null) {
                    Intent  theIntent = new Intent(getApplication(), EditContact.class);
                    theIntent.putExtra("_Id", contactsList.get("_Id"));              // auto generated key
                    theIntent.putExtra("conPhone", contactsList.get("conPhone"));   // contact phone
                    theIntent.putExtra("conName",  contactsList.get("conName"));    // contact name

                    startActivity(theIntent); // display map with user location and destination for tracking
                    finish();
                }
            }
        });

    }
}


    // ADD BUTTON on click allow user to create a location
    public void showAddLocation(View view) {
        Intent addIntent = new Intent(getApplication(), NewContact.class);
        startActivity(addIntent);
    }

    @SuppressWarnings("unused")
    private ContextWrapper getAppContext() {
        // TODO Auto-generated method stub
        return null;
    }
}
