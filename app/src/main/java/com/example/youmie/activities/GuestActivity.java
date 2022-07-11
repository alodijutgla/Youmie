package com.example.youmie.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.youmie.R;
import com.example.youmie.utils.DatabaseUtils;
import com.example.youmie.utils.RecycleAdapter;
import com.example.youmie.utils.User;

import java.util.ArrayList;

public class GuestActivity extends AppCompatActivity {

    ArrayList<String> titlesList = new ArrayList<>();
    ArrayList<String> pricesList = new ArrayList<>();
    ArrayList<String> descList = new ArrayList<>();
    ArrayList<Integer> imagesList = new ArrayList<>();
    ArrayList<User> userArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        DatabaseUtils databaseUtils = new DatabaseUtils(this);
        userArrayList = databaseUtils.readUsersTable();

        postToList();

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecycleAdapter(titlesList, pricesList, descList, imagesList));

    }

    private void addToList(String title, String price, String description, Integer image) {
        titlesList.add(title);
        pricesList.add(price);
        descList.add(description);
        imagesList.add(image);
    }

    private void postToList() {
        for (User user : userArrayList) {
            int imageToAdd;
            switch (user.getFoodType()) {
                case "Asian":
                    imageToAdd = R.drawable.asian;
                    break;
                case "Greek":
                    imageToAdd = R.drawable.greek;
                    break;
                default:
                    imageToAdd = R.mipmap.ic_launcher_round;
            }
            addToList(user.getUsername(), user.getPrice(), user.getPlaceName(), imageToAdd);
        }
    }
}