package com.example.parkir.api;

import com.example.parkir.model.login.LoginModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface api {
    @FormUrlEncoded
    @POST("logins/")
    Call<LoginModel> logins(
            @Field("username") String username,
            @Field("password") String password
    );
}