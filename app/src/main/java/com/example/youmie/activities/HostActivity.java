package com.example.youmie.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youmie.R;
import com.example.youmie.utils.DatabaseUtils;
import com.example.youmie.utils.SharedPrefUtils;

import es.dmoral.toasty.Toasty;

public class HostActivity extends AppCompatActivity {

    private EditText nameOfPlace, price, description;
    private String foodType;

    /**
     * onCreate() method gathers all the input data the host user wants to provide
     * It also configures a drop down menu for choosing the type of food from a list.
     * If the REGISTER button is clicked the data is checked and if correct, it is inserted to the DB
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        // get values provided by the user in the different editText
        nameOfPlace = findViewById(R.id.placeName);
        price = findViewById(R.id.price);
        description = findViewById(R.id.edtDescription);

        // instantiate the button for sign up
        Button registerButton = findViewById(R.id.registerBtn);

        // get username value from the stored Shared Preferences
        String username = SharedPrefUtils.getUsername(this);

        // created new object DatabaseUtils so we can interact with the database
        DatabaseUtils databaseUtils = new DatabaseUtils(this);

        configureDropDownMenu();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // gets the text from the editText
                String nameOfPlaceText = nameOfPlace.getText().toString();
                String priceText = price.getText().toString();
                String descriptionText = description.getText().toString();

                // if any of the fields is not present the user will be alerted
                if (nameOfPlaceText.isEmpty() || priceText.isEmpty() || foodType.isEmpty() || descriptionText.isEmpty()) {
                    Toasty.warning(HostActivity.this,
                            "Please provide all fields",
                            Toast.LENGTH_SHORT, true).show();
                } else {
                    // if all fields are provided the data is added to the database for the current username
                    if (databaseUtils.insertHostData(username, nameOfPlaceText, foodType, Float.parseFloat(priceText), descriptionText)) {
                        Toasty.success(HostActivity.this,
                                "Entry registered successfully!",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ChoiceActivity.class));
                    }
                }
            }
        });
    }

    /**
     * configureDropDownMenu() creates a drop down menu using the spinner object and displaying
     * all items of the stringsList provided
     */
    void configureDropDownMenu() {
        Spinner typeFoodSpinner = findViewById(R.id.spinnerTypeFood);

        // declaring a new ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, getResources().getStringArray(R.array.typeOfFoods)) {
            @Override
            public boolean isEnabled(int position) {
                // the first item is not selectable as it is used for the hint message `Type of food...`
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // setting the color to gray so we can display the hint message
                    // `Type of food...` for the first item
                    tv.setTextColor(Color.GRAY);
                } else {
                    // all the selectable items of the list are displayed in black
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        // setting the resource spinner_item.xml to the spinnerArrayAdapter so the spinner
        // is displayed as customized in that resource
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        typeFoodSpinner.setAdapter(spinnerArrayAdapter);

        typeFoodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // get the value of the item at the position that the user chose
                foodType = (String) parent.getItemAtPosition(position);
                if (position == 0) {
                    // if selected first item we return an empty string as it is just a hint message
                    foodType = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}