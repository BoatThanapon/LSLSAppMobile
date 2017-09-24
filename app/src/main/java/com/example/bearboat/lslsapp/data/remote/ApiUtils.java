package com.example.bearboat.lslsapp.data.remote;

/**
 * Created by BearBoat on 2017-09-23.
 */

public class ApiUtils {
    private ApiUtils() {
    }
    //127.0.0.1
    //192.168.1.46

    public static final String BASE_URL = "http://192.168.1.46:56447/api/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
