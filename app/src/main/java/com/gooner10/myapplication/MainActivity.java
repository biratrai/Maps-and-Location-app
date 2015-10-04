package com.gooner10.myapplication;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    MyLocationListener mGpsListener;
    MyLocationListener mNetworkListener;
    String LOG_TAG = MainActivity.class.getSimpleName();
    Looper mLooper;
    TextView mTextView;

    NetworkProviderStatusReceiver networkProviderStatusReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.location);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            // Create a Handler Thread and get Looper
            HandlerThread mHandlerThread = new HandlerThread("ActivityLocationThread");
            mHandlerThread.start();
            mLooper = mHandlerThread.getLooper();

            if (confirmNetworkProviderEnabled(locationManager)) {

                // Register to receive broadcast
                networkProviderStatusReceiver = new NetworkProviderStatusReceiver();
                networkProviderStatusReceiver.start(this);

                // If Enabled get LocationListener running and pass it to background HandlerThread
                mNetworkListener = new MyLocationListener();

                if (mNetworkListener != null) {
                    mNetworkListener.setMainActivity(this);
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mNetworkListener, mLooper);
            }
//            mGpsListener = new MyLocationListener();

//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mGpsListener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLocation(Location location) {
        mTextView = (TextView) findViewById(R.id.location);
        mTextView.setText("Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude());
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Remove LocationUpdates for GPS Listener
        if (mGpsListener != null) {
            locationManager.removeUpdates(mGpsListener);
            mGpsListener = null;
        }

        // Remove LocationUpdates for Network Listener
        if (mNetworkListener != null) {
            locationManager.removeUpdates(mNetworkListener);
            mNetworkListener = null;
        }

        // Remove BroadCastReceiver
        if (networkProviderStatusReceiver != null) {
            networkProviderStatusReceiver.stop(this);
        }

        // Remove Looper
        if (mLooper != null) {
            mLooper.quit();
            mLooper = null;
        }
    }

    public void onAccurateCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(true);
        criteria.setAltitudeRequired(true);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> matchingProviderName = locationManager.getProviders(criteria, false);
        for (String providerName : matchingProviderName) {
            LocationProvider provider = locationManager.getProvider(providerName);

        }
    }

    public boolean confirmNetworkProviderEnabled(LocationManager locationManager) {
        boolean isEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isEnabled) {
            Log.d(LOG_TAG, "Network Provider is disabled!");
        }
        return isEnabled;
    }

//    public void confirmAirplaneModeDisabled(LocationManager locationManager) {
//        boolean isOff;
//        isOff = Settings.System.getInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
//        if (isOff) {
//            Log.d(LOG_TAG,"Network Provider is disabled!");
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
