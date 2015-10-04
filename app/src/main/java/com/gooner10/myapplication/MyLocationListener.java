package com.gooner10.myapplication;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class MyLocationListener implements LocationListener {
    final String LOG_TAG = MyLocationListener.class.getSimpleName();

    @Override
    public void onLocationChanged(Location location) {

        String logMessage = LogHelper.FormatLocationInfo(location);
        Log.d(LOG_TAG, "onLocationChanged " + logMessage);
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
