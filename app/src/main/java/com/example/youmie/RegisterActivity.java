package com.example.youmie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText username = (EditText) findViewById(R.id.username_reg);
        EditText password = (EditText) findViewById(R.id.password_reg);
        EditText passwordRep = (EditText) findViewById(R.id.password_rep_reg);
        Button signUp = (Button) findViewById(R.id.signUpBtn);
        DatabaseUtils databaseUtils = new DatabaseUtils(this);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userText = username.getText().toString();
                String passText = password.getText().toString();
                String passRepText = passwordRep.getText().toString();

                if (userText.isEmpty() || passText.isEmpty() || passRepText.isEmpty()) {
                    Toasty.warning(RegisterActivity.this,
                            "Please provide all fields",
                            Toast.LENGTH_SHORT, true).show();
                } else {
                    if (!passText.equals(passRepText)) {
                        Toasty.error(RegisterActivity.this,
                                "Please make sure your passwords match",
                                Toast.LENGTH_SHORT, true).show();
                    } else {
                        if (databaseUtils.verifyUsername(userText)) {
                            Toasty.warning(RegisterActivity.this,
                                    "Sorry but this username is already taken ;)",
                                    Toast.LENGTH_SHORT, true).show();
                        } else if (databaseUtils.checkIfNonUniquePwd(passText)) {
                            Toasty.warning(RegisterActivity.this,
                                    "This password is not available, please try again",
                                    Toast.LENGTH_LONG, true).show();
                        } else {
                            if (databaseUtils.insertUser(userText, passText)) {
                                Toasty.success(RegisterActivity.this,
                                        "Registered successfully",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
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