package com.example.francisqueins.breakingtheice;

/**
 * Created by Francisqueins on 6/15/2017.
 * This program shows user's location and
 * I.C.E. alerts around n miles
 */

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.common.api.GoogleApiClient;

import static android.widget.Toast.LENGTH_SHORT;
import static com.example.francisqueins.breakingtheice.R.id.map;

public class showMapWithAlerts extends AppCompatActivity implements
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener

{
    private GoogleMap googleMap; //GoogleMap class
    private GoogleApiClient mGoogleApiClient;  //To manipulate user location
    LocationRequest mLocationRequest = new LocationRequest();//To manipulate current location
    private double currentLatitude;
    private double currentLongitude;
    private Marker     mDestiny;
    public String      locLat;   // target destination coordinates
    public String      locLong;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts); //activity_alerts is the screen for this program

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

            //Create a marker in the map at a given position with a title
            LatLng myPosition = new LatLng(currentLatitude, currentLongitude);
            Marker marker = googleMap.addMarker(new MarkerOptions().
                    position(myPosition).title("Break the I.C.E.")
                    .snippet("You are here")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.you_are_here)));

            //place the map on user's position
            CameraUpdateFactory.newLatLng(new LatLng(currentLatitude, currentLongitude));
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
            getAlerts();
        } else {

            Toast.makeText(getApplicationContext(), "Your Location Not Found", Toast.LENGTH_LONG).show();
        }
    }

    protected void createLocationRequest() {

        //Toast.makeText(getApplicationContext(), "Create location", Toast.LENGTH_LONG).show();
        //remove location updates so that it resets
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //restart location updates with the new interval
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    protected void getAlerts() {

        String curLatitude = "37.42374543";
        String curLongitude = "-99.99999999";
        String type = "locatePoints"; // this is the operation we want to execute

        // **** executing the async task ************************************
        Toast.makeText(getApplicationContext(), "This is the check alert program.", LENGTH_SHORT).show();
        myAsyncTask MyasyncTask = new myAsyncTask(this);
        MyasyncTask.execute(type, curLatitude, curLongitude);
        //finish();

        // *******************************************************************


        // sql sentences to get alert locations should be filled in arrays

        LatLng[] alert = new LatLng[3];
        alert[0] = new LatLng(34.032824, -117.615989);
        alert[1] = new LatLng(34.030763, -117.611908);
        alert[2] = new LatLng(34.024041, -117.609837);
        for (int i = 0; i < alert.length; i++) {
            Marker marker = googleMap.addMarker(new MarkerOptions().
                    position(alert[i]).title("Alert")
                    .snippet("I.C.E.")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ice_is_here)));
        }

    }



    public void onMapLongClick(LatLng point) {
        Toast.makeText(getApplicationContext(), "Long click", Toast.LENGTH_LONG).show();
        mDestiny = googleMap.addMarker(new MarkerOptions()
                .position(point)
                .snippet("Destination selected")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.destiny)));
        locLat  = Double.toString(point.latitude);
        locLong = Double.toString(point.longitude);
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
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

}
