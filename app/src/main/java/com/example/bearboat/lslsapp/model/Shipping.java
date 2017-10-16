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
    @SerializedName("receiverName")
    @Expose
    private String receiverName;
    @SerializedName("statusOfTransportation")
    @Expose
    private Boolean statusOfTransportation;
    @SerializedName("jobIsActive")
    @Expose
    private Boolean jobIsActive;
    @SerializedName("receiveDateTime")
    @Expose
    private String receiveDateTime;
    @SerializedName("shippingDocImage")
    @Expose
    private Object shippingDocImage;
    @SerializedName("shippingNote")
    @Expose
    private String shippingNote;

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

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
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

    public String getReceiveDateTime() {
        return receiveDateTime;
    }

    public void setReceiveDateTime(String receiveDateTime) {
        this.receiveDateTime = receiveDateTime;
    }

    public Object getShippingDocImage() {
        return shippingDocImage;
    }

    public void setShippingDocImage(Object shippingDocImage) {
        this.shippingDocImage = shippingDocImage;
    }

    public String getShippingNote() {
        return shippingNote;
    }

    public void setShippingNote(String shippingNote) {
        this.shippingNote = shippingNote;
    }

}