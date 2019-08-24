package com.example.parkir.model.chartmonthly;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data_ {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("goverment")
    @Expose
    private Integer goverment;
    @SerializedName("total_vehicle")
    @Expose
    private Integer totalVehicle;
    @SerializedName("month")
    @Expose
    private Integer month;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("type")
    @Expose
    private String type;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getGoverment() {
        return goverment;
    }

    public void setGoverment(Integer goverment) {
        this.goverment = goverment;
    }

    public Integer getTotalVehicle() {
        return totalVehicle;
    }

    public void setTotalVehicle(Integer totalVehicle) {
        this.totalVehicle = totalVehicle;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}