package com.example.parkir.model.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data_ {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nominal")
    @Expose
    private Integer nominal;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("sender")
    @Expose
    private Sender sender;
    @SerializedName("receiver")
    @Expose
    private Receiver receiver;

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

}