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
        if (intent.hasExtra("image_key")) {
            Bitmap bitmap = (Bitmap) intent.getParcelableExtra("image_key");
            if (bitmap != null) {
                ImageView imageView = findViewById(R.id.resImage);
                imageView.setImageBitmap(bitmap);
            }
        }

        if (intent.hasExtra("username_key")) {
            TextView textView = findViewById(R.id.user_det);
            textView.setText(intent.getStringExtra("username_key"));
        }

        if (intent.hasExtra("detail_key")) {
            TextView textView = findViewById(R.id.descr_det);
            textView.setText(intent.getStringExtra("detail_key"));
        }
    }


}