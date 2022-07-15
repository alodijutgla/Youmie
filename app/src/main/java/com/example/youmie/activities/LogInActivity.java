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

    /**
     * onCreate() calls the methods runAnimation() with the imageView provided and then the
     * startLoginActivity() is called ensuring the animation is finished before switching to the next activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // get values provided by the user in the editText
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        // if the SharedPreferences are present, meaning that the user already logged in before closing the app,
        // once the app is started again, the user will be automatically redirected to the
        // ChoiceActivity and will not need to log in again
        if (SharedPrefUtils.getUsername(this) != null && !SharedPrefUtils.getUsername(this).isEmpty()) {
            startActivity(new Intent(getApplicationContext(), ChoiceActivity.class));
        }

        verifyLoginData();
        redirectToRegisterPageOnClick();
    }

    /**
     * verifyLoginData() asserts that once the LOGIN button is clicked, the username and password are
     * provided and it is present in the database. If so the data is saved in the SharedPreferences
     * for saving the state when opening again the application.
     */
    private void verifyLoginData() {
        loginBtn = (Button) findViewById(R.id.loginbtn);

        DatabaseUtils databaseUtils = new DatabaseUtils(this);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // gets the text from username and password from the editText
                String userText = username.getText().toString();
                String passText = password.getText().toString();

                // if username or password are not provided we send a warning message so the user
                // can provide all the needed data fields
                if (userText.isEmpty() || passText.isEmpty()) {
                    Toasty.warning(LogInActivity.this, "Please provide all fields",
                            Toast.LENGTH_SHORT, true).show();
                } else {
                    // if username and data are provided we check if an entry is present in the database
                    if (databaseUtils.checkUserData(userText, passText)) {
                        // saving username and password to the SharedPreferences for later
                        SharedPrefUtils.saveUsername(userText, getApplicationContext());
                        SharedPrefUtils.savePassword(passText, getApplicationContext());
                        Toasty.success(LogInActivity.this, "Login successfully!",
                                Toast.LENGTH_SHORT).show();
                        // if all is correct we start the ChoiceActivity and pass the username
                        // to it via the extra parameters of the Intent
                        Intent intent = new Intent(getApplicationContext(), ChoiceActivity.class);
                        intent.putExtra("username_key", userText);
                        startActivity(intent);
                    } else {
                        // if usrename or password are not in the DB we notify via a Toast
                        Toasty.error(LogInActivity.this,
                                "The username or password is incorrect. Please try again",
                                Toast.LENGTH_LONG, true).show();
                    }
                }
            }
        });
    }

    /**
     * redirectToRegisterPageOnClick() starts the RegisterActivity if the user presses the button
     * with the text `Don't have an accaount? Sign up!`
     */
    private void redirectToRegisterPageOnClick() {
        signUp = (Button) findViewById(R.id.signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }
}