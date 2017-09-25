package com.example.bearboat.lslsapp.manager;

/**
 * Created by BearBoat on 2017-09-23.
 */

import com.example.bearboat.lslsapp.model.LoginStatus;
import com.example.bearboat.lslsapp.model.TruckDriver;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    // account/checklogin?username=driverA&password=driver
    @GET("account/checklogin")
    Call<LoginStatus> CheckLogin(@Query("username") String username,
                                 @Query("password") String password);

    @GET("truckdriver/1")
    Call<TruckDriver> GetTruckDrivers();
}
