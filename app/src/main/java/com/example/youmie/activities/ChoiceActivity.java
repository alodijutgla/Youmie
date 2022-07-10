package com.example.youmie.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.youmie.R;
import com.example.youmie.utils.SharedPrefUtils;

public class ChoiceActivity extends AppCompatActivity {

    TextView receiver_msg;
    Button hostButton, guestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        displayUsername();
        redirectToHostPageOnClick();
        redirectToGuestPageOnClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.log_out) {
            SharedPrefUtils.savePassword("", this);
            SharedPrefUtils.saveUsername("", this);
            startActivity(new Intent(this, LogInActivity.class));
            return true;
        }
        throw new IllegalStateException("Unexpected value: " + item.getItemId());
    }

    private void displayUsername() {
        receiver_msg = (TextView) findViewById(R.id.received_value);

        String username;
        Intent intent = getIntent();
        if (intent.hasExtra("username_key")) {
            username = intent.getStringExtra("username_key");
        } else {
            username = SharedPrefUtils.getUsername(this);
        }
        receiver_msg.setText(String.format("Hello %s", username));
    }

    private void redirectToHostPageOnClick() {
        hostButton = (Button) findViewById(R.id.hostBtn);
        hostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChoiceActivity.this, HostActivity.class));
            }
        });
    }

    private void redirectToGuestPageOnClick() {
        guestButton = (Button) findViewById(R.id.guestBtn);
        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChoiceActivity.this, GuestActivity.class));
            }
        });
    }
}
