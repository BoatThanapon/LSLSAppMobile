package com.example.bearboat.lslsapp.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MySharedPreference {

    public static final String TRUCK_DRIVER_ID = "TRUCK_DRIVER_ID";
    public static final String LAST_ACTIVE_TIME = "LAST_ACTIVE_TIME";


    public static void putPref(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPref(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public static void clearPref(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().clear().commit();
    }
}
