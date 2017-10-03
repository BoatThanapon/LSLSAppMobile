package com.example.bearboat.lslsapp.tool;

import android.content.Context;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UserInterfaceUtils {

    public static void showToast(Context context, String text) {

        Toast toast = Toast.makeText(context,
                text,
                Toast.LENGTH_SHORT);
        toast.show();
    }
}
