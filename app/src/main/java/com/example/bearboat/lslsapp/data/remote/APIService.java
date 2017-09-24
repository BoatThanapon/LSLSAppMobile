package com.example.bearboat.lslsapp.data.remote;

/**
 * Created by BearBoat on 2017-09-23.
 */

import com.example.bearboat.lslsapp.models.TruckDriver;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @POST("/Account/CheckLogin/")
    @FormUrlEncoded
    Call<TruckDriver> LoginTruckDriver(@Field("username") String username,
                                       @Field("password") String password);

    @GET("/TruckDriver/1")
    Call<TruckDriver> GetTruckDrivers();
}
