package com.example.bearboat.lslsapp.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Validator {

    public static boolean isStringEmpty(final String text) {

        return text == null || text.isEmpty();
    }

    public static boolean isUsernameValid(final String username) {

        final int usernameLength = username.length();
        return usernameLength < 4 || usernameLength > 16;
    }

    public static boolean isPasswordValid(final String password) {

        final int passwordLength = password.length();
        return passwordLength < 4 || passwordLength > 16;
    }

    public final static boolean isConnected(Context context) {
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
