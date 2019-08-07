package com.example.parkir.api;

import com.example.parkir.model.account.AccountModel;
import com.example.parkir.model.login.LoginModel;
import com.example.parkir.model.payment.PaymentModel;

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

    @GET("accounts/")
    Call<AccountModel> account(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("payments/")
    Call<PaymentModel> payments(@Header("Authorization") String token);
}