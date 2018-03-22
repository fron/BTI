package com.example.francisqueins.breakingtheice;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import static android.R.attr.type;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            String[] PERMISSIONS = {android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION};
            if (ContextCompat.checkSelfPermission(this, String.valueOf(PERMISSIONS))
                    != PackageManager.PERMISSION_GRANTED) {

                Log.d("permission", "LOCATION permission denied to SEND_SMS - requesting it");
                requestPermissions(PERMISSIONS, PERMISSION_REQUEST_CODE);
            }
        }



    }

    // This method creates the menu on the app
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Called when a options menu item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // To control user's selection
        int id = item.getItemId();

        // We check what menu item was clicked and show a Toast
        if (id == R.id.menuOption1) {

            Intent  theIntent = new Intent(getApplication(), showMapWithAlerts.class);
            startActivity(theIntent); // display map with user location and destination for tracking

            return true;

            // If exit was clicked close the app
        } else if (id == R.id.menuOption2) {
            Intent  theIntent = new Intent(getApplication(), createAnAlert.class);
            startActivity(theIntent); // display map with user location and destination for tracking

            return true;

        } else if (id == R.id.menuOption3) {
            Intent  theIntent = new Intent(getApplication(), sendAmessage.class);
            startActivity(theIntent); // display map with user location and destination for tracking

            return true;
        } else if (id == R.id.menuOption4) {
            Intent  theIntent = new Intent(getApplication(), manageContacts.class);
            startActivity(theIntent); // display map with user location and destination for tracking

            return true;
        } else if (id == R.id.menuOption5) {
            Context context = getApplicationContext();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}


//  Toast examples

//  Toast.makeText(getApplicationContext(), "After the background.",
//          LENGTH_SHORT).show();
//  Toast.makeText(getActivity(), "This is my Toast message!",
//          Toast.LENGTH_LONG).show();
//  Toast.makeText(myAsyncTask.this, "YOUR MESSAGE", LENGTH_SHORT).show();