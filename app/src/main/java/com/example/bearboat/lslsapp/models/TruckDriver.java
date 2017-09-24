package com.example.bearboat.lslsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TruckDriver {

    @SerializedName("truckDriverId")
    @Expose
    private Integer truckDriverId;
    @SerializedName("truckDriverUsername")
    @Expose
    private String truckDriverUsername;
    @SerializedName("truckDriverPassword")
    @Expose
    private String truckDriverPassword;
    @SerializedName("truckDriverConfirmPassword")
    @Expose
    private String truckDriverConfirmPassword;
    @SerializedName("truckDriverFullname")
    @Expose
    private String truckDriverFullname;
    @SerializedName("truckDriverGender")
    @Expose
    private String truckDriverGender;
    @SerializedName("truckDriverCitizenId")
    @Expose
    private String truckDriverCitizenId;
    @SerializedName("truckDriverDriverLicenseId")
    @Expose
    private String truckDriverDriverLicenseId;
    @SerializedName("truckDriverAddress")
    @Expose
    private String truckDriverAddress;
    @SerializedName("truckDriverBirthdate")
    @Expose
    private String truckDriverBirthdate;
    @SerializedName("truckDriverEmail")
    @Expose
    private String truckDriverEmail;
    @SerializedName("truckDriverTelephoneNo")
    @Expose
    private String truckDriverTelephoneNo;
    @SerializedName("truckId")
    @Expose
    private String truckId;

    public Integer getTruckDriverId() {
        return truckDriverId;
    }

    public void setTruckDriverId(Integer truckDriverId) {
        this.truckDriverId = truckDriverId;
    }

    public String getTruckDriverUsername() {
        return truckDriverUsername;
    }

    public void setTruckDriverUsername(String truckDriverUsername) {
        this.truckDriverUsername = truckDriverUsername;
    }

    public String getTruckDriverPassword() {
        return truckDriverPassword;
    }

    public void setTruckDriverPassword(String truckDriverPassword) {
        this.truckDriverPassword = truckDriverPassword;
    }

    public String getTruckDriverConfirmPassword() {
        return truckDriverConfirmPassword;
    }

    public void setTruckDriverConfirmPassword(String truckDriverConfirmPassword) {
        this.truckDriverConfirmPassword = truckDriverConfirmPassword;
    }

    public String getTruckDriverFullname() {
        return truckDriverFullname;
    }

    public void setTruckDriverFullname(String truckDriverFullname) {
        this.truckDriverFullname = truckDriverFullname;
    }

    public String getTruckDriverGender() {
        return truckDriverGender;
    }

    public void setTruckDriverGender(String truckDriverGender) {
        this.truckDriverGender = truckDriverGender;
    }

    public String getTruckDriverCitizenId() {
        return truckDriverCitizenId;
    }

    public void setTruckDriverCitizenId(String truckDriverCitizenId) {
        this.truckDriverCitizenId = truckDriverCitizenId;
    }

    public String getTruckDriverDriverLicenseId() {
        return truckDriverDriverLicenseId;
    }

    public void setTruckDriverDriverLicenseId(String truckDriverDriverLicenseId) {
        this.truckDriverDriverLicenseId = truckDriverDriverLicenseId;
    }

    public String getTruckDriverAddress() {
        return truckDriverAddress;
    }

    public void setTruckDriverAddress(String truckDriverAddress) {
        this.truckDriverAddress = truckDriverAddress;
    }

    public String getTruckDriverBirthdate() {
        return truckDriverBirthdate;
    }

    public void setTruckDriverBirthdate(String truckDriverBirthdate) {
        this.truckDriverBirthdate = truckDriverBirthdate;
    }

    public String getTruckDriverEmail() {
        return truckDriverEmail;
    }

    public void setTruckDriverEmail(String truckDriverEmail) {
        this.truckDriverEmail = truckDriverEmail;
    }

    public String getTruckDriverTelephoneNo() {
        return truckDriverTelephoneNo;
    }

    public void setTruckDriverTelephoneNo(String truckDriverTelephoneNo) {
        this.truckDriverTelephoneNo = truckDriverTelephoneNo;
    }

    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }

}
