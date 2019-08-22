package com.example.parkir.model.history;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HistoryList {
    @SerializedName("historyList")
    private ArrayList<HistoryModel> historyList;

    public ArrayList<HistoryModel> getHistoryArrayList(){
        return historyList;
    }

    public void setEmployeeArrayList(ArrayList<HistoryModel> historyArrayList) {
        this.historyList = historyArrayList;
    }
}