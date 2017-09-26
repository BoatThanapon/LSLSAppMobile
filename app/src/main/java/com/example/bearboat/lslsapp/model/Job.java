package com.example.bearboat.lslsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Job {

    @SerializedName("jobAssignmentId")
    @Expose
    private Integer jobAssignmentId;
    @SerializedName("transportationInf")
    @Expose
    private Object transportationInf;
    @SerializedName("shippingId")
    @Expose
    private Integer shippingId;
    @SerializedName("truckDriver")
    @Expose
    private Object truckDriver;
    @SerializedName("truckDriverId")
    @Expose
    private Integer truckDriverId;
    @SerializedName("jobAssignmentDate")
    @Expose
    private String jobAssignmentDate;
    @SerializedName("startingPointJob")
    @Expose
    private String startingPointJob;
    @SerializedName("latitudeStartJob")
    @Expose
    private Double latitudeStartJob;
    @SerializedName("longitudeStartJob")
    @Expose
    private Double longitudeStartJob;
    @SerializedName("destinationJob")
    @Expose
    private String destinationJob;
    @SerializedName("latitudeDesJob")
    @Expose
    private Double latitudeDesJob;
    @SerializedName("longitudeDesJob")
    @Expose
    private Double longitudeDesJob;
    @SerializedName("jobAssignmentStatus")
    @Expose
    private Boolean jobAssignmentStatus;

    public Integer getJobAssignmentId() {
        return jobAssignmentId;
    }

    public void setJobAssignmentId(Integer jobAssignmentId) {
        this.jobAssignmentId = jobAssignmentId;
    }

    public Object getTransportationInf() {
        return transportationInf;
    }

    public void setTransportationInf(Object transportationInf) {
        this.transportationInf = transportationInf;
    }

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
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

    public String getJobAssignmentDate() {
        return jobAssignmentDate;
    }

    public void setJobAssignmentDate(String jobAssignmentDate) {
        this.jobAssignmentDate = jobAssignmentDate;
    }

    public String getStartingPointJob() {
        return startingPointJob;
    }

    public void setStartingPointJob(String startingPointJob) {
        this.startingPointJob = startingPointJob;
    }

    public Double getLatitudeStartJob() {
        return latitudeStartJob;
    }

    public void setLatitudeStartJob(Double latitudeStartJob) {
        this.latitudeStartJob = latitudeStartJob;
    }

    public Double getLongitudeStartJob() {
        return longitudeStartJob;
    }

    public void setLongitudeStartJob(Double longitudeStartJob) {
        this.longitudeStartJob = longitudeStartJob;
    }

    public String getDestinationJob() {
        return destinationJob;
    }

    public void setDestinationJob(String destinationJob) {
        this.destinationJob = destinationJob;
    }

    public Double getLatitudeDesJob() {
        return latitudeDesJob;
    }

    public void setLatitudeDesJob(Double latitudeDesJob) {
        this.latitudeDesJob = latitudeDesJob;
    }

    public Double getLongitudeDesJob() {
        return longitudeDesJob;
    }

    public void setLongitudeDesJob(Double longitudeDesJob) {
        this.longitudeDesJob = longitudeDesJob;
    }

    public Boolean getJobAssignmentStatus() {
        return jobAssignmentStatus;
    }

    public void setJobAssignmentStatus(Boolean jobAssignmentStatus) {
        this.jobAssignmentStatus = jobAssignmentStatus;
    }

}