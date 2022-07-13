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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        nameOfPlace = findViewById(R.id.placeName);
        price = findViewById(R.id.price);
        description = findViewById(R.id.edtDescription);
        Button registerButton = findViewById(R.id.registerBtn);
        String username = SharedPrefUtils.getUsername(this);
        DatabaseUtils databaseUtils = new DatabaseUtils(this);

        configureDropDownMenu();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameOfPlaceText = nameOfPlace.getText().toString();
                String priceText = price.getText().toString();
                String descriptionText = description.getText().toString();

                if (nameOfPlaceText.isEmpty() || priceText.isEmpty() || foodType.isEmpty() || descriptionText.isEmpty()) {
                    Toasty.warning(HostActivity.this,
                            "Please provide all fields",
                            Toast.LENGTH_SHORT, true).show();
                } else {
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

    void configureDropDownMenu() {
        Spinner typeFoodSpinner = findViewById(R.id.spinnerTypeFood);

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, getResources().getStringArray(R.array.typeOfFoods)) {
            @Override
            public boolean isEnabled(int position) {
                // First item used for the hint message
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        typeFoodSpinner.setAdapter(spinnerArrayAdapter);

        typeFoodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                foodType = (String) parent.getItemAtPosition(position);
                if (position == 0) {
                    foodType = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}