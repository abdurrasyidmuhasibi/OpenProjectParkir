package com.example.parkir.model.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data_ {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nominal")
    @Expose
    private Integer nominal;
    @SerializedName("location_detail")
    @Expose
    private String locationDetail;
    @SerializedName("vehicle_registration")
    @Expose
    private String vehicleRegistration;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("sender")
    @Expose
    private Sender sender;
    @SerializedName("receiver")
    @Expose
    private Receiver receiver;
    @SerializedName("parking_type")
    @Expose
    private ParkingType parkingType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNominal() {
        return nominal;
    }

    public void setNominal(Integer nominal) {
        this.nominal = nominal;
    }

    public String getLocationDetail() {
        return locationDetail;
    }

    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
    }

    public String getVehicleRegistration() {
        return vehicleRegistration;
    }

    public void setVehicleRegistration(String vehicleRegistration) {
        this.vehicleRegistration = vehicleRegistration;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public ParkingType getParkingType() {
        return parkingType;
    }

    public void setParkingType(ParkingType parkingType) {
        this.parkingType = parkingType;
    }

}