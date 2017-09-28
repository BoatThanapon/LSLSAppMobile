package com.example.bearboat.lslsapp.manager;

/**
 * Created by BearBoat on 2017-09-23.
 */

import com.example.bearboat.lslsapp.model.Job;
import com.example.bearboat.lslsapp.model.LoginStatus;
import com.example.bearboat.lslsapp.model.Shipping;
import com.example.bearboat.lslsapp.model.TruckDriver;
import com.example.bearboat.lslsapp.model.TruckLocation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface APIService {

    // account/checklogin?username=driverA&password=driver
    @GET("account/checklogin")
    Call<LoginStatus> checkLogin(@Query("username") String username,
                                 @Query("password") String password);

    @GET("truckdriver")
    Call<TruckDriver> getTruckDriverInfo(@Query("truckDriverId") String truckDriverId);

    @GET("jobassignment")
    Call<List<Job>> getJobsList(@Query("truckDriverId") String truckDriverId);

    @GET("transportationinf")
    Call<Shipping> getShippingInfo(@Query("shippingId") String shippingId);

    @PUT("trucklocation")
    Call<TruckLocation> updateTruckLocation(@Query("truckDriverId") String truckDriverId);

    @PUT("transportationinf")
    Call<Shipping> updateStatusShipping(@Query("shippingId") String shippingId);

}
