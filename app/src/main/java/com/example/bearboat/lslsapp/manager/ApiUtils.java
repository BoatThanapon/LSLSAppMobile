package com.example.bearboat.lslsapp.manager;

/**
 * Created by BearBoat on 2017-09-23.
 */

public class ApiUtils {
    private ApiUtils() {
    }

    public static final String BASE_URL = "http://lsls.azurewebsites.net/api/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
