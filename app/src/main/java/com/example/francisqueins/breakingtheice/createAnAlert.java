package com.example.francisqueins.breakingtheice;

/**
 * Created by Francisqueins on 6/23/2017.
 * This program allows a user to create an
 * I.C.E. alert
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.francisqueins.breakingtheice.R.id.map;
import static com.example.francisqueins.breakingtheice.login.MY_PREFS_NAME;

public class createAnAlert extends AppCompatActivity implements
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMapLongClickListener,
        LocationListener
{
    private GoogleMap googleMap; //GoogleMap class
    private GoogleApiClient mGoogleApiClient;  //To manipulate user location
    LocationRequest mLocationRequest = new LocationRequest();//To manipulate current location
    private double currentLatitude;
    private double currentLongitude;
    private Marker iceLocation;  // marker for an ICE on progress
    public String  locLat;   // target destination coordinates
    public String  locLong;
    private static final int PERMISSION_REQUEST_CODE = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create); //activity_alerts is the screen for this program

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        // verify we can interact with the Google Map
        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().
                        findFragmentById(map)).getMap();   // map is the name of the fragment
            }                                                   // defined in the layout

            /* MAP_TYPE_NORMAL: Basic map with roads.
            MAP_TYPE_SATELLITE: Satellite view with roads.
            MAP_TYPE_TERRAIN: Terrain view without roads.
            MAP_TYPE_HYBRID: City view with buildings
            */
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); // satellite map with roads
            googleMap.setMyLocationEnabled(true); // enable my location
            googleMap.setTrafficEnabled(true); // turn traffic layer on
            googleMap.setIndoorEnabled(true); // enables indoor maps
            googleMap.setBuildingsEnabled(true); // Turns on 3D buildings
            googleMap.getUiSettings().setZoomControlsEnabled(true); // show zoom butttons
            googleMap.setOnMapLongClickListener(this); // to select a target destination

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onConnected(Bundle connectionHint) {

        //onConnected is automatic, this doesn't mean a current location is acquired
        //this is to get the current location with google play services
        createLocationRequest();
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {

            currentLatitude = mLastLocation.getLatitude();
            currentLongitude = mLastLocation.getLongitude();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude,currentLongitude), 15));

            // placing a marker on current position, this should be removed if the
            // marker and the the current location dot don't match
            LatLng myPosition = new LatLng(currentLatitude, currentLongitude);
            Marker marker = googleMap.addMarker(new MarkerOptions().
                    position(myPosition).title("BTI")
                    .snippet("You are here")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.you_are_here)));

            //place the map on user's position
            CameraUpdateFactory.newLatLng(new LatLng(currentLatitude, currentLongitude));
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

            String msg = "Make a LONG CLICK over a\r\nplace where an I.C.E. inspection \r\n" +
                    "is in progress, then SAVE\r\n \r\nPlease use this option wisely";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(getApplicationContext(), "Sorry, we can't find your location", Toast.LENGTH_LONG).show();
        }
    }

    protected void createLocationRequest() {

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (LocationListener) this);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //restart location updates with the new interval
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
    }


    @Override
    public void onMapLongClick(LatLng point) {
        iceLocation = googleMap.addMarker(new MarkerOptions()
                .position(point)
                .snippet("I.C.E inspection is in progress")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.destiny)));
        locLat  = Double.toString(point.latitude);
        locLong = Double.toString(point.longitude);

        String msg = "Press: Save Alert";
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

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

    }

    @Override
    protected void onStart() {
        // connect googleapiclient
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        // disconnect googleapiclient
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "Connection failed", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    public void saveLocation(View view){
        // Send a message to contacts
        Intent  theIntent = new Intent(getApplication(), sendAmessage.class);
        startActivity(theIntent); // display map with user location and destination for tracking

        // recovering phone from memory (saved on class login)

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String usrPhoneM = prefs.getString("usrPhone", null);//"null" is the default value.

        String altCreatedA = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String altTimeA    = new SimpleDateFormat("HHmmss").format(new Date());
        String altActiveA = "A";
        String type = "alert"; // in myAsyncTask the operation to execute is a new alert

        myAsyncTask MyasyncTask = new myAsyncTask(this);
        MyasyncTask.execute(type, usrPhoneM, locLat, locLong, altCreatedA, altTimeA, altActiveA);
        finish();
    }

    //Sends an SMS message to another device
    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
