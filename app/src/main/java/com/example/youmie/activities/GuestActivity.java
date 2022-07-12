package com.example.youmie.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.youmie.R;
import com.example.youmie.utils.DatabaseUtils;
import com.example.youmie.utils.RecycleAdapter;
import com.example.youmie.utils.Host;

import java.util.ArrayList;
import java.util.Iterator;

import es.dmoral.toasty.Toasty;

public class GuestActivity extends AppCompatActivity {

    ArrayList<String> titlesList = new ArrayList<>();
    ArrayList<String> pricesList = new ArrayList<>();
    ArrayList<String> typeOfFoodList = new ArrayList<>();
    ArrayList<String> descList = new ArrayList<>();
    ArrayList<Integer> imagesList = new ArrayList<>();
    ArrayList<Host> hostsArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        DatabaseUtils databaseUtils = new DatabaseUtils(this);
        hostsArrayList = databaseUtils.readUsersTable();

        try {
            postToList();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecycleAdapter(titlesList, pricesList, typeOfFoodList, descList, imagesList));

    }

    private void addToList(String title, String price, String typeOfFood, String description, Integer image) {
        titlesList.add(title);
        typeOfFoodList.add(typeOfFood);
        pricesList.add(price);
        descList.add(description);
        imagesList.add(image);
    }

    private void removeUsersThatAreNoHosts() throws IllegalAccessException {
//        for (Host host : hostsArrayList) {
//            if (host.checkNull()) {
//                hostsArrayList.remove(host);
//            }
//        }

        for (Iterator<Host> iterator = hostsArrayList.iterator(); iterator.hasNext(); ) {
            Host value = iterator.next();
            if (value.checkNull()) {
                iterator.remove();
            }
        }
    }

    private void postToList() throws IllegalAccessException {
        removeUsersThatAreNoHosts();
        if (hostsArrayList.isEmpty()) {
            Toasty.error(GuestActivity.this,
                    "There are no hosts yet!",
                    Toast.LENGTH_LONG, true).show();
            return;
        }

        for (Host host : hostsArrayList) {
            int imageToAdd;
            switch (host.getFoodType()) {
                case "Asian":
                    imageToAdd = R.drawable.asian;
                    break;
                case "Greek":
                    imageToAdd = R.drawable.greek;
                    break;
                case "Indian":
                    imageToAdd = R.drawable.indian;
                    break;
                case "Italian":
                    imageToAdd = R.drawable.italian;
                    break;
                case "Fast food":
                    imageToAdd = R.drawable.fast_food;
                    break;
                case "Spanish":
                    imageToAdd = R.drawable.spanish;
                    break;
                default:
                    imageToAdd = R.mipmap.ic_launcher_round;
            }
            addToList(host.getUsername(), host.getPrice() + "€", host.getFoodType(), host.getPlaceName(), imageToAdd);
        }
    }
}