package com.example.parkir.api;

import com.example.parkir.model.PaymentParking.PaymentParkingModel;
import com.example.parkir.model.account.AccountModel;
import com.example.parkir.model.account.Assignment;
import com.example.parkir.model.daftar.RegisterModel;
import com.example.parkir.model.login.LoginModel;
import com.example.parkir.model.notification.NotificationModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @FormUrlEncoded
    @PUT("assignments/")
    Call<Assignment> assignment(
            @Header("Authorization") String token,
            @Field("location_name") String namaLokasi,
            @Field("location_address") String alamatLokasi,
            @Field("district") String kecamatan,
            @Field("city") String kota
    );

    @GET("accounts/")
    Call<AccountModel> account(@Header("Authorization") String token);

    @GET("accounts/parkirs/{accountid}")
    Call<AccountModel> account_parkir(@Header("Authorization") String token, @Path("accountid") int accountid);

    @FormUrlEncoded
    @POST("payments/")
    Call<PaymentParkingModel> payments(
            @Header("Authorization") String token,
            @Field("receiverid") String receiverid,
            @Field("parking_typeid") String parkingtypeid,
            @Field("vehicle_registration") String vehicle_registration
    );

    @FormUrlEncoded
    @POST("notifications/")
    Call<NotificationModel> notifications(
            @Field("token") String fcmToken,
            @Field("nominal") String nominal,
            @Field("vehicle_registration") String vehicle_registration
    );
}