package com.example.parkir.api;

import com.example.parkir.model.PaymentParking.PaymentParkingModel;
import com.example.parkir.model.account.AccountModel;
import com.example.parkir.model.login.LoginModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface api {
    @FormUrlEncoded
    @POST("logins/")
    Call<LoginModel> logins(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("accounts/")
    Call<AccountModel> account(@Header("Authorization") String token);

    @GET("accounts/parkirs/{accountid}")
    Call<AccountModel> account_parkir(@Header("Authorization") String token, @Path("accountid") int accountid);

    @FormUrlEncoded
    @POST("payments/")
    Call<PaymentParkingModel> payments(
            @Header("Authorization") String token,
            @Field("receiverid") String receiverid
    );
}