package com.gooner10.myapplication;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class MyLocationListener implements LocationListener {
    final String LOG_TAG = MyLocationListener.class.getSimpleName();
    MainActivity mainActivity;

    public void setMainActivity(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void onLocationChanged(final Location location) {

        String logMessage = LogHelper.FormatLocationInfo(location);
        Log.d(LOG_TAG, "onLocationChanged " + logMessage);
        Log.d(LOG_TAG, "onLocationChanged Thread" + Thread.currentThread());

        // Running on UI Thread from HandleThread
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.setLocation(location);
            }
        });
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(LOG_TAG, "onStatusChanged " + provider + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(LOG_TAG, "onProviderEnabled " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(LOG_TAG, "onProviderDisabled " + provider);
    }
}
