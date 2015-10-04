package com.gooner10.myapplication;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class LogHelper {
    static final String timeStampFormat = "yyyy-MM-dd'T'HH:mm:ss";
    static final String timsStampTimeZoneId = "UTC";

    public static String FormatLocationInfo(String provider, double latitude, double longitude, float accuracy, float time) {
        SimpleDateFormat timeStampFormatter = new SimpleDateFormat();
        timeStampFormatter.setTimeZone(TimeZone.getTimeZone(timsStampTimeZoneId));

        String timeStamp = timeStampFormatter.format(time);

        String logMessage = String.format("%s | lat/long=%f/%f | accuracy=%f | Time=%s", provider,
                latitude, longitude, accuracy, timeStamp);
        return logMessage;
    }

    public static String FormatLocationInfo(Location location) {
        String provider = location.getProvider();
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        float accuracy = location.getAccuracy();
        long time = location.getTime();

        return LogHelper.FormatLocationInfo(provider, lat, lng, accuracy, time);
    }
}
