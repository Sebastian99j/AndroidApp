package com.zadanierekrutacyjne.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.zadanierekrutacyjne.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = (ImageView) findViewById(R.id.mapImage);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BufferedInputStream reader = new BufferedInputStream(openFileInput("bitmap.txt"));
            int read;
            byte[] buffer = new byte[32000];
            while ((read = reader.read(buffer)) >= 0) {
                baos.write(buffer, 0, read);
            }
        } catch (IOException fnf) {
            fnf.printStackTrace();
        }

        String base64Image = baos.toString().split(",")[0];
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        imageView.setImageBitmap(decodedByte);
    }

    public void back(View view) {
        Intent intent = new Intent(this, DetailsActivity.class);
        startActivity(intent);
    }
}