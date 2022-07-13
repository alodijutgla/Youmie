package com.example.youmie.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youmie.R;
import com.example.youmie.utils.SharedPrefUtils;

import org.w3c.dom.Text;


public class DetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Intent intent = getIntent();

        if (intent.hasExtra("placename_key")) {
            TextView textView = findViewById(R.id.placename_key);
            textView.setText(intent.getStringExtra("placename_key"));
        }

        if (intent.hasExtra("image_key")) {
            Bitmap bitmap = (Bitmap) intent.getParcelableExtra("image_key");
            if (bitmap != null) {
                ImageView imageView = findViewById(R.id.resImage);
                imageView.setImageBitmap(bitmap);
            }
        }

        if (intent.hasExtra("typeOfFood_key")) {
            TextView textView = findViewById(R.id.typeOfFood_key);
            textView.setText(intent.getStringExtra("typeOfFood_key"));
        }

        if (intent.hasExtra("username_key")) {
            TextView textView = findViewById(R.id.user_det);
            textView.setText(String.format("Host: %s", intent.getStringExtra("username_key")));
        }

        if (intent.hasExtra("price_key")) {
            TextView textView = findViewById(R.id.price_key);
            textView.setText(intent.getStringExtra("price_key"));
        }

        if (intent.hasExtra("description_key")) {
            TextView textView = findViewById(R.id.descr_det);
            textView.setText(intent.getStringExtra("description_key"));
        }
    }


}