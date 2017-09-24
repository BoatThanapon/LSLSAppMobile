package com.example.bearboat.lslsapp.tool;

public class Validator {

    public static boolean isStringEmpty( final String text ) {

        return text == null || text.isEmpty();
    }

    public static boolean isUsernameValid( final String username ) {

        final int usernameLength = username.length();
        return usernameLength < 4 || usernameLength > 16;
    }

    public static boolean isPasswordValid( final String password ) {

        final int passwordLength = password.length();
        return passwordLength < 4 || passwordLength > 16;
    }
}
