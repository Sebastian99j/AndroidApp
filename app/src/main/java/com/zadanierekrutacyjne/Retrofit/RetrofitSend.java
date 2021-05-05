package com.zadanierekrutacyjne.Retrofit;

import android.graphics.Bitmap;
import android.util.Log;

import com.zadanierekrutacyjne.Retrofit.Methods;
import com.zadanierekrutacyjne.Retrofit.Model;
import com.zadanierekrutacyjne.Retrofit.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitSend {
    public static int ACCESS = 2;

    public static int accessing(String addedEmail, String addedPassword) throws JSONException {
        Methods method = RetrofitClient.getRetrofitInstance().create(Methods.class);

        Map<String, String> headerMap=new HashMap<>();
        headerMap.put("Accept", "application/json");
        headerMap.put("Content-Type", "application/json");
        headerMap.put("X-Authorization","access_token");

//        Call<Model> call = method.getUserLogin(addedEmail, addedPassword);

        JSONObject js = new JSONObject();
        js.put("email", addedEmail);
        js.put("password", addedPassword);
        System.out.println(js);
        Call<Model> call = method.getUserLogin(headerMap,js);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.code() == 200){
                    ACCESS = 1;
                }
                else {
                    ACCESS = 0;
                }
                Log.e("MainActivity","onResponse: code: "+response.code());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e("MainActivity", "onFailure: "+t.getMessage());
                    ACCESS = 0;
            }
        });
        return ACCESS;
    }

    public static void registering(String addedEmail, String addedPassword) {
        Methods method = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = method.registerUser(addedEmail, addedPassword);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Log.e("MainActivity","onResponse: code: "+response.code());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e("MainActivity", "onFailure: "+t.getMessage());
            }
        });
    }

    public static void sendImage(File file){
        Methods method = RetrofitClient.getRetrofitInstance().create(Methods.class);
        RequestBody requestBody = RequestBody.create(file, MediaType.parse("image/*"));
        MultipartBody.Part partRequest = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        Call<RequestBody> call = method.uploadImage(partRequest);

        call.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                Log.e("MainActivity","onResponse: code: "+response.code());
            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                Log.e("MainActivity", "onFailure: "+t.getMessage());
            }
        });
    }
}