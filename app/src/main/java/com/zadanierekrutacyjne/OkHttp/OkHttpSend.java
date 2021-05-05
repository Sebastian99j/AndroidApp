package com.zadanierekrutacyjne.OkHttp;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zadanierekrutacyjne.Activities.FourthActivity;
import com.zadanierekrutacyjne.ApiErrors.ErrorUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpSend {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String BASE_URL = "http://192.168.192.5:8000/";
    private static Integer ACCESS = 2;
    private static OkHttpClient client = new OkHttpClient();

    public static Integer sendDataToLogin(String addedEmail, String addedPassword) throws JSONException {
        String url = BASE_URL+"getuser";

        JSONObject js = new JSONObject();
        js.put("email", addedEmail);
        js.put("password", addedPassword);

        RequestBody body = RequestBody.create(String.valueOf(js), JSON);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                ACCESS = 0;
                Log.e("MainActivity","onFailure: code: "+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code()==200){
                    ACCESS = 1;
                }
                else {
                    ACCESS = 0;
                }
                Log.e("MainActivity", "onResponse: "+response.code()+" " +response.body().byteString().utf8());
            }
        });
        return ACCESS;
    }

    public static void registerUser(String addedEmail, String addedPassword) throws JSONException{
        String url = BASE_URL+"adduser";

        JSONObject js = new JSONObject();
        js.put("email", addedEmail);
        js.put("password", addedPassword);

        RequestBody body = RequestBody.create(String.valueOf(js), JSON);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("MainActivity","onFailure: code: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.e("MainActivity", "onResponse: "+response.code() + " " + response.body().byteString().utf8());
            }
        });
    }

    public static void sendJson(String data, String suffixAdress, String iduser, String token) throws JSONException{
        String url = BASE_URL+suffixAdress;

        JSONObject js = new JSONObject(data);

        RequestBody body = RequestBody.create(String.valueOf(js), JSON);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("x-token", token)
                .header("id-user", iduser)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("MainActivity","onFailure: code: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.e("MainActivity", "onResponse: "+response.code() + " " + response.body().byteString().utf8());
            }
        });
    }

    public static void getStatistic(String header){
        String url = BASE_URL+"statistic";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .header("id_user", header)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("MainActivity","onFailure: code: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();
                }
            }
        });
    }

    public static void uploadImage(Bitmap bitmap, Context context){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);

        String url = BASE_URL+"upload-file";

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "image",
                        RequestBody.create(encodedImage,  MediaType.parse("image/*")))
                .build();

        Request request = new Request.Builder()
                .url(url)
//                .header("Content-type", "multipart/form-data")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("MainActivity","onFailure: code: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.e("MainActivity", "onResponse: "+response.code() + " " + response.body().byteString().utf8());
            }
        });
    }

    public static void sendFile(File file){
        String url = BASE_URL+"upload-file";

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(),
                        RequestBody.create(file, MediaType.parse("multipart/form-data")))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("MainActivity","onFailure: code: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.e("MainActivity", "onResponse: "+response.code() + " " + response.body().byteString().utf8());
            }
        });
    }
}