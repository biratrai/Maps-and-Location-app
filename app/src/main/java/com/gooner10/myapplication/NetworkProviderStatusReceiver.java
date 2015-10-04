package com.gooner10.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

public class NetworkProviderStatusReceiver extends BroadcastReceiver {
    final String LOG_TAG = NetworkProviderStatusReceiver.class.getSimpleName();

    public NetworkProviderStatusReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Bundle extras = intent.getExtras();

        Log.d(LOG_TAG, "Monitor Location Broadcast action receiver: " + action);

        if (action.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            boolean state = extras.getBoolean("state");
            Log.d(LOG_TAG, "ACTION_AIRPLANE_MODE_CHANGED to " + (state ? "ON" : "OFF"));
        } else if (action.equalsIgnoreCase(ConnectivityManager.CONNECTIVITY_ACTION)) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = connectivityManager.getActiveNetworkInfo();

            Log.d(LOG_TAG, "WiFi is available:  " + (wifiInfo.isAvailable() ? "YES" : "NO"));
        }

    }

    public void start(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(this, intentFilter);
    }

    public void stop(Context context) {
        context.unregisterReceiver(this);
    }
}
