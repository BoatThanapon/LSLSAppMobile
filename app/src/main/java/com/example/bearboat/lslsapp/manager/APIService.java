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

    //For Login TruckDriver by username and password
    // Account/CheckTruckDriverLogin?username=driverA&password=driver
    @GET("Account/CheckTruckDriverLogin")
    Call<LoginStatus> checkLogin(@Query("username") String username,
                                 @Query("password") String password);

    //For Get Information of TruckDriverId
    // TruckDriver/GetTruckDriverInfo?truckDriverId=2
    @GET("TruckDriver/GetTruckDriverInfo")
    Call<TruckDriver> getTruckDriverInfo(@Query("truckDriverId") String truckDriverId);

    //For Get List Job Assignment of TruckDriverId
    // JobAssignment/GetListJobAssignment?truckDriverId=1
    @GET("JobAssignment/GetListJobAssignment")
    Call<List<Job>> getJobsList(@Query("truckDriverId") String truckDriverId);

    //For Get Information of Shipping
    // TransportationInf/GetTransportationInf?shippingId=9
    @GET("TransportationInf/GetTransportationInf")
    Call<Shipping> getShippingInfo(@Query("shippingId") String shippingId);

    //For Update TruckLocation
    // TruckLocation/UpdateTruckLocation?truckDriverId=2
    @PUT("TruckLocation/UpdateTruckLocation")
    Call<TruckLocation> updateTruckLocation(@Query("truckDriverId") String truckDriverId);

    //For Update status by statusOfTransportation in Shipping class = true(completed) or false(not complete)
    // TransportationInf/UpdateTransportationInf?shippingId=9
    @PUT("TransportationInf/UpdateTransportationInf")
    Call<Shipping> updateStatusShipping(@Query("shippingId") String shippingId);

}
