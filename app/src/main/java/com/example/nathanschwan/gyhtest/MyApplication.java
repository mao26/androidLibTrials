
package com.example.nathanschwan.gyhtest;

import android.app.Application;
import android.util.Log;

import android.support.multidex.MultiDexApplication;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.regions.Regions;

/**
 * Created by nathanschwan on 10/18/16.
 */

public class MyApplication extends Application {

    private final static String LOG_TAG = MyApplication.class.getSimpleName();

    //amazon signin cognito services.
    public CognitoCachingCredentialsProvider credentialsProvider;


    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "MyApplication.onCreate - Initializing application...");
        super.onCreate();
        initializeApplication();
        Log.d(LOG_TAG, "MyApplication.onCreate - MyApplication initialized OK");
    }

    private void initializeApplication() {

        // Initialize the AWS Mobile Client
        AWSMobileClient.initializeMobileClientIfNecessary(getApplicationContext());
        credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(), // Context
                "us-east-1:ce4c9743-c8b5-440e-bf65-782a6f021a66", // Identity Pool ID
                Regions.US_EAST_1 // Region
        );

        // ... Put any application-specific initialization logic here ...
    }

    public CognitoCachingCredentialsProvider getProvider(){
        return credentialsProvider;
    }

}
