package com.example.youmie.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.youmie.R;
import com.example.youmie.utils.RecycleAdapters;

import java.util.ArrayList;

public class GuestActivity extends AppCompatActivity {

    ArrayList<String> titlesList = new ArrayList<>();
    ArrayList<String> descList = new ArrayList<>();
    ArrayList<Integer> imagesList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        postToList();

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecycleAdapters(titlesList, descList, imagesList));

    }

    private void addToList(String title, String description, Integer image) {
        titlesList.add(title);
        descList.add(description);
        imagesList.add(image);
    }

    private void postToList() {
        for (int i = 1; i < 25; i++) {
            addToList("Title " + i, "Description:" + i, R.mipmap.ic_launcher_round);
        }
    }
}