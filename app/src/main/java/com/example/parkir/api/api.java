package com.example.parkir.api;

import com.example.parkir.model.PaymentParking.PaymentParkingModel;
import com.example.parkir.model.account.AccountModel;
import com.example.parkir.model.daftar.RegisterModel;
import com.example.parkir.model.login.LoginModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface api {
    @FormUrlEncoded
    @POST("logins/")
    Call<LoginModel> logins(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("registers/")
    Call<RegisterModel> register(
        @Field("roleid") int roleid,
        @Field("username") String username,
        @Field("password") String password,
        @Field("full_name") String nama,
        @Field("email") String email,
        @Field("address") String alamat
    );

    @GET("accounts/")
    Call<AccountModel> account(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("payments/")
    Call<PaymentParkingModel> payments(
            @Header("Authorization") String token,
            @Field("receiverid") String receiverid
    );
}