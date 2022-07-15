package com.example.youmie.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.youmie.utils.DatabaseUtils;
import com.example.youmie.R;
import com.example.youmie.utils.SharedPrefUtils;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity {

    /**
     * onCreate() method gathers all the data provided by the user via multiple editText.
     * Then it verifies all the data, and if correct, the SharedPreferences will be stored
     * and the user will be registered and automatically logged in. Then it redirects to the ChoiceActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // get values provided by the user in the different editText
        EditText username = (EditText) findViewById(R.id.username_reg);
        EditText password = (EditText) findViewById(R.id.password_reg);
        EditText passwordRep = (EditText) findViewById(R.id.password_rep_reg);

        // instantiate the button for sign up
        Button signUp = (Button) findViewById(R.id.signUpBtn);
        // created new object DatabaseUtils so we can interact with the database
        DatabaseUtils databaseUtils = new DatabaseUtils(this);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userText = username.getText().toString();
                String passText = password.getText().toString();
                String passRepText = passwordRep.getText().toString();

                // if any of the fields is not present the user will be alerted
                if (userText.isEmpty() || passText.isEmpty() || passRepText.isEmpty()) {
                    Toasty.warning(RegisterActivity.this,
                            "Please provide all fields",
                            Toast.LENGTH_SHORT, true).show();
                } else {
                    // if the password does not match with the repetition password the user is alerted
                    if (!passText.equals(passRepText)) {
                        Toasty.error(RegisterActivity.this,
                                "Please make sure your passwords match",
                                Toast.LENGTH_SHORT, true).show();
                    } else {
                        // check if the user is already present in the database
                        if (databaseUtils.verifyUsername(userText)) {
                            Toasty.warning(RegisterActivity.this,
                                    "Sorry but this username is already taken ;)",
                                    Toast.LENGTH_SHORT, true).show();
                        // check that the password is not used by another user
                        } else if (databaseUtils.checkIfNonUniquePwd(passText)) {
                            Toasty.warning(RegisterActivity.this,
                                    "This password is not available, please try again",
                                    Toast.LENGTH_LONG, true).show();
                        } else {
                            // if all data is correct, the user is added to the database
                            if (databaseUtils.insertUser(userText, passText)) {
                                // saving username and password to the SharedPreferences for later
                                SharedPrefUtils.saveUsername(userText, getApplicationContext());
                                SharedPrefUtils.savePassword(passText, getApplicationContext());
                                Toasty.success(RegisterActivity.this,
                                        "Registered successfully",
                                        Toast.LENGTH_SHORT).show();
                                // if all is correct we start the ChoiceActivity and pass the username
                                // to it via the extra parameters of the Intent
                                Intent intent = new Intent(getApplicationContext(), ChoiceActivity.class);
                                intent.putExtra("username_key", userText);
                                startActivity(intent);
                            } else {
                                Toasty.error(RegisterActivity.this,
                                        "Registration failed",
                                        Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    }
                }
            }
        });

    }
}