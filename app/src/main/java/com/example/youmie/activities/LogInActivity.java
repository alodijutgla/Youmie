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

public class LogInActivity extends AppCompatActivity {

    Button signUp, loginBtn;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        if (SharedPrefUtils.getUsername(this) != null && !SharedPrefUtils.getUsername(this).isEmpty()) {
            startActivity(new Intent(getApplicationContext(), ChoiceActivity.class));
        }

        verifyLoginData();
        redirectToRegisterPageOnClick();
    }

    private void verifyLoginData() {
        loginBtn = (Button) findViewById(R.id.loginbtn);

        DatabaseUtils databaseUtils = new DatabaseUtils(this);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userText = username.getText().toString();
                String passText = password.getText().toString();

                if (userText.isEmpty() || passText.isEmpty()) {
                    Toasty.warning(LogInActivity.this, "Please provide all fields",
                            Toast.LENGTH_SHORT, true).show();
                } else {
                    if (databaseUtils.checkUserData(userText, passText)) {
                        SharedPrefUtils.saveUsername(userText, getApplicationContext());
                        SharedPrefUtils.savePassword(passText, getApplicationContext());
                        Toasty.success(LogInActivity.this, "Login successfully!",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ChoiceActivity.class);
                        intent.putExtra("username_key", userText);
                        startActivity(intent);
                    } else {
                        Toasty.error(LogInActivity.this,
                                "The username or password is incorrect. Please try again",
                                Toast.LENGTH_LONG, true).show();
                    }
                }
            }
        });
    }

    private void redirectToRegisterPageOnClick() {
        signUp = (Button) findViewById(R.id.signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
            }
        });
    }
}