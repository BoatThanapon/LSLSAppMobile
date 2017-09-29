package com.example.bearboat.lslsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shipping {

    @SerializedName("shippingId")
    @Expose
    private Integer shippingId;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("dateOfTransportation")
    @Expose
    private String dateOfTransportation;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("startingPoint")
    @Expose
    private String startingPoint;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("employer")
    @Expose
    private String employer;
    @SerializedName("recieverName")
    @Expose
    private String recieverName;
    @SerializedName("statusOfTransportation")
    @Expose
    private Boolean statusOfTransportation;
    @SerializedName("jobIsActive")
    @Expose
    private Boolean jobIsActive;
    @SerializedName("recieveDateTime")
    @Expose
    private Object recieveDateTime;

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDateOfTransportation() {
        return dateOfTransportation;
    }

    public void setDateOfTransportation(String dateOfTransportation) {
        this.dateOfTransportation = dateOfTransportation;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getRecieverName() {
        return recieverName;
    }

    public void setRecieverName(String recieverName) {
        this.recieverName = recieverName;
    }

    public Boolean getStatusOfTransportation() {
        return statusOfTransportation;
    }

    public void setStatusOfTransportation(Boolean statusOfTransportation) {
        this.statusOfTransportation = statusOfTransportation;
    }

    public Boolean getJobIsActive() {
        return jobIsActive;
    }

    public void setJobIsActive(Boolean jobIsActive) {
        this.jobIsActive = jobIsActive;
    }

    public Object getRecieveDateTime() {
        return recieveDateTime;
    }

    public void setRecieveDateTime(Object recieveDateTime) {
        this.recieveDateTime = recieveDateTime;
    }

}