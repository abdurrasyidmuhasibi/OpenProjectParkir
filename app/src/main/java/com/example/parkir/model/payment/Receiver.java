package com.example.parkir.model.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Receiver {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("balance")
    @Expose
    private Integer balance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

}