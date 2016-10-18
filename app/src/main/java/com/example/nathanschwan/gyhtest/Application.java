
package com.example.nathanschwan.gyhtest;

import android.os.Bundle;
import android.util.Log;

import android.support.multidex.MultiDexApplication;

import com.amazonaws.mobile.AWSMobileClient;

/**
 * Created by nathanschwan on 10/18/16.
 */

public class Application extends MultiDexApplication {

    private final static String LOG_TAG = Application.class.getSimpleName();

    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "Application.onCreate - Initializing application...");
        super.onCreate();
        initializeApplication();
        Log.d(LOG_TAG, "Application.onCreate - Application initialized OK");
    }

    private void initializeApplication() {

        // Initialize the AWS Mobile Client
        AWSMobileClient.initializeMobileClientIfNecessary(getApplicationContext());

        // ... Put any application-specific initialization logic here ...
    }
}
