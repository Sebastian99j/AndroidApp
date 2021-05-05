package com.zadanierekrutacyjne.Retrofit;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Methods {

//    @FormUrlEncoded
//    @POST("getuser")
//    Call<Model> getUserLogin(
//            @Field("email") String email,
//            @Field("password") String password
//    );

    @POST("getuser")
    Call<Model> getUserLogin(
            @HeaderMap Map<String, String> headers,
            @Body JSONObject jsonObject);

    @FormUrlEncoded
    @POST("adduser")
    Call<Model> registerUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @Multipart
    @POST("upload-file")
    Call<RequestBody> uploadImage(@Part MultipartBody.Part part);
}
