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

    /**
     * onCreate() method gets all the data from the Intent of the previous activity and displays it
     * in the current activity through TextView and ImageView
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        // get the Intent from previous activity
        Intent intent = getIntent();

        // set the place name attribute to the textView
        if (intent.hasExtra("placename_key")) {
            TextView textView = findViewById(R.id.placename_key);
            textView.setText(intent.getStringExtra("placename_key"));
        }

        // set the image attribute to the imageView
        if (intent.hasExtra("image_key")) {
            // get the Bitmap from the Intent
            Bitmap bitmap = (Bitmap) intent.getParcelableExtra("image_key");
            if (bitmap != null) {
                ImageView imageView = findViewById(R.id.resImage);
                imageView.setImageBitmap(bitmap);
            }
        }

        // set the type of food attribute to the textView
        if (intent.hasExtra("typeOfFood_key")) {
            TextView textView = findViewById(R.id.typeOfFood_key);
            textView.setText(intent.getStringExtra("typeOfFood_key"));
        }

        // set the username attribute to the textView
        if (intent.hasExtra("username_key")) {
            TextView textView = findViewById(R.id.user_det);
            // add the prefix Host to the username and set it to the textview
            textView.setText(String.format("Host: %s", intent.getStringExtra("username_key")));
        }

        // set the price attribute to the textView
        if (intent.hasExtra("price_key")) {
            TextView textView = findViewById(R.id.price_key);
            textView.setText(intent.getStringExtra("price_key"));
        }

        // set the description attribute to the textView
        if (intent.hasExtra("description_key")) {
            TextView textView = findViewById(R.id.descr_det);
            textView.setText(intent.getStringExtra("description_key"));
        }
    }


}