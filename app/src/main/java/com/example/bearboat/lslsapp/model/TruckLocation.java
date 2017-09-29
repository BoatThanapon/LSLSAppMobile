package com.example.bearboat.lslsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TruckLocation {

    @SerializedName("truckLocationId")
    @Expose
    private Integer truckLocationId;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("truckCurrentTime")
    @Expose
    private Object truckCurrentTime;
    @SerializedName("truckCurrentAddress")
    @Expose
    private Object truckCurrentAddress;
    @SerializedName("truckDriver")
    @Expose
    private Object truckDriver;
    @SerializedName("truckDriverId")
    @Expose
    private Integer truckDriverId;

    public Integer getTruckLocationId() {
        return truckLocationId;
    }

    public void setTruckLocationId(Integer truckLocationId) {
        this.truckLocationId = truckLocationId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Object getTruckCurrentTime() {
        return truckCurrentTime;
    }

    public void setTruckCurrentTime(Object truckCurrentTime) {
        this.truckCurrentTime = truckCurrentTime;
    }

    public Object getTruckCurrentAddress() {
        return truckCurrentAddress;
    }

    public void setTruckCurrentAddress(Object truckCurrentAddress) {
        this.truckCurrentAddress = truckCurrentAddress;
    }

    public Object getTruckDriver() {
        return truckDriver;
    }

    public void setTruckDriver(Object truckDriver) {
        this.truckDriver = truckDriver;
    }

    public Integer getTruckDriverId() {
        return truckDriverId;
    }

    public void setTruckDriverId(Integer truckDriverId) {
        this.truckDriverId = truckDriverId;
    }

}