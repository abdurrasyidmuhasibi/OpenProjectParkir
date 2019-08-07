
package com.example.parkir.model.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountModel {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
