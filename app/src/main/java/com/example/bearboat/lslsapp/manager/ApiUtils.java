package com.example.bearboat.lslsapp.manager;

/**
 * Created by BearBoat on 2017-09-23.
 */

public class ApiUtils {
    private ApiUtils() {
    }
    //127.0.0.1
    //192.168.1.46

    public static final String BASE_URL = "http://lsls.azurewebsites.net/api/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
