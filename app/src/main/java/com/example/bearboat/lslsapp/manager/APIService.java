package com.example.bearboat.lslsapp.manager;

/**
 * Created by BearBoat on 2017-09-23.
 */

import com.example.bearboat.lslsapp.model.ResponseStatus;
import com.example.bearboat.lslsapp.model.TruckDriver;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @POST("/Account/CheckLogin")
    @FormUrlEncoded
    Call<ResponseStatus> CheckLogin(@Field("username") String username,
                                    @Field("password") String password);


    @GET("truckdriver/1")
    Call<TruckDriver> GetTruckDrivers();
}
