package com.example.bearboat.lslsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginStatus {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("truckDriverId")
    @Expose
    private Integer truckDriverId;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTruckDriverId() {
        return truckDriverId;
    }

    public void setTruckDriverId(Integer truckDriverId) {
        this.truckDriverId = truckDriverId;
    }

}