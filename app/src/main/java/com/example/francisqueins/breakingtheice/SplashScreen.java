package com.example.francisqueins.breakingtheice;

/**
 * Created by Francisqueins on 6/15/2017.
 * This program just shows the first screen
 * during SPLASH_SCREEN_DELAY seconds before the main screen
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashScreen extends Activity{

    private static int SPLASH_SCREEN_DELAY = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Executed after timer is finished (Opens MainActivity)
                //Intent intent = new Intent(SplashScreen.this, login.class);
                //startActivity(intent);
                // Temporal to avoid login (for testing purposes)
                Intent theIntent = new Intent(getApplication(), MainActivity.class);
                startActivity(theIntent); // display map with user location and destination for tracking


                // Kills this Activity
                finish();
            }
        }, SPLASH_SCREEN_DELAY);
    }
}
