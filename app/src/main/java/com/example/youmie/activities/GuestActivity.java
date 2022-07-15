package com.example.youmie.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youmie.R;
import com.example.youmie.utils.DatabaseUtils;
import com.example.youmie.utils.RecycleAdapter;
import com.example.youmie.utils.Host;
import com.example.youmie.utils.SharedPrefUtils;

import java.util.ArrayList;
import java.util.Iterator;

import es.dmoral.toasty.Toasty;

public class GuestActivity extends AppCompatActivity {

    private String username;

    private final ArrayList<String> usernameList = new ArrayList<>();
    private final ArrayList<String> pricesList = new ArrayList<>();
    private final ArrayList<String> typeOfFoodList = new ArrayList<>();
    private final ArrayList<String> nameOfPlaceList = new ArrayList<>();
    private final ArrayList<String> descriptionList = new ArrayList<>();
    private final ArrayList<Integer> imagesList = new ArrayList<>();
    private ArrayList<Host> hostsArrayList;

    /**
     * onCreate() method initializes the DatabaseUtils and displays the username value in an editText.
     * It gets the value of the database and puts it to a List<Host>.
     * Then it updated each and every value of the list. Finally all this parameters are passed to
     * a RecyclerView which shows the List in the Activity with all the fields
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        displayUsername();

        DatabaseUtils databaseUtils = new DatabaseUtils(this);
        hostsArrayList = databaseUtils.readUsersTable();

        try {
            postToList();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // get recycleView id from the layout
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        // set the Linear layout for the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // set the adapter which has all the data read from the database
        recyclerView.setAdapter(new RecycleAdapter(usernameList, nameOfPlaceList, typeOfFoodList, pricesList, descriptionList, imagesList));
    }

    /**
     * displayUsername() it displays the name of the user at the top using the TextView
     */
    private void displayUsername() {
        // get the adress of the TextView to put the username
        TextView receivedUser = (TextView) findViewById(R.id.receivedUser);
        Intent intent = getIntent();
        if (intent.hasExtra("username_key")) {
            // if the username comes from the previous activity via the intent we use it
            username = intent.getStringExtra("username_key");
        } else {
            // if we don't have the username from the previous activity, we get it from the Shared Preferences
            username = SharedPrefUtils.getUsername(this);
        }
        receivedUser.setText(String.format("Hello %s", username));
    }

    /**
     * addToList() adds all the attributes passed to the method to the corresponding list
     */
    private void addToList(String user, String nameOfPlace, String typeOfFood, String price, String description, Integer image) {
        usernameList.add(user);
        nameOfPlaceList.add(nameOfPlace);
        typeOfFoodList.add(typeOfFood);
        pricesList.add(price);
        descriptionList.add(description);
        imagesList.add(image);
    }

    /**
     * removeUsersThatAreNoHosts() clears the users that do not have the host data in their profile
     * from the hostList to show. Also asserts that the user does not see its own hosts in the shown lists
     */
    private void removeUsersThatAreNoHosts() throws IllegalAccessException {
        // we iterate over the hostsArrayList element by element
        for (Iterator<Host> iterator = hostsArrayList.iterator(); iterator.hasNext(); ) {
            Host value = iterator.next();
            // if any value is Null we remove the entry from the list
            if (value.checkNull()) {
                iterator.remove();
            }
            // if the value contains its own username we remove it from the list
            else if (value.getUsername().equals(username)) {
                iterator.remove();
            }
        }
    }

    /**
     * postToList() posts all the data from the database to the corresponding lists so the
     * recyclerView can show the right entries
     */
    private void postToList() throws IllegalAccessException {
        // call to the method
        removeUsersThatAreNoHosts();

        // the list is empty, meaning that there are no host entries an error message is displayed
        if (hostsArrayList.isEmpty()) {
            Toasty.error(GuestActivity.this,
                    "There are no hosts yet!",
                    Toast.LENGTH_LONG, true).show();
            return;
        }

        // it selects the right image to choose for each type of food
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
            // adds all the elements into the list so the recycleview can display them
            addToList(host.getUsername(), host.getPlaceName(), host.getFoodType(), host.getPrice() + "€", host.getDescription(), imageToAdd);
        }
    }
}