package com.example.bearboat.lslsapp.tool;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.bearboat.lslsapp.activity.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validator {

    //test
    public static boolean isStringEmpty(final String text) {

        return text == null || text.isEmpty();
    }

    //test
    public static boolean isUsernameValid(final String username) {

        final int usernameLength = username.length();
        return !(usernameLength < 4 || usernameLength > 16);
    }

    //test
    public static boolean isPasswordValid(final String password) {

        final int passwordLength = password.length();
        return !(passwordLength < 4 || passwordLength > 16);
    }

    public final static boolean isConnected(Context context) {
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public final static boolean isActiveOverHalfHour(Context context) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        String current = simpleDateFormat.format(new Date());
        String lastActive = MySharedPreference.getPref(MySharedPreference.LAST_ACTIVE_TIME, context);
        Boolean result = false;

        if (lastActive != null) {
            try {
                Date lastFetchDate = simpleDateFormat.parse(lastActive);
                Date currentDate = simpleDateFormat.parse(current);

                long diffMin = (int) (currentDate.getTime() - lastFetchDate.getTime()) / (1000 * 60);

                Log.i("TEST", "isActiveOverHalfHour: " + diffMin);

                if (diffMin >= 30) {
                    result = true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                result = false;
            }
        }
        return result;
    }
}
