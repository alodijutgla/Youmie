package com.example.youmie.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.youmie.R;
import com.example.youmie.utils.SharedPrefUtils;

public class ChoiceActivity extends AppCompatActivity {

    TextView receiver_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        receiver_msg = (TextView) findViewById(R.id.received_value_id);
        String toDisplay = "";

        Intent intent = getIntent();
        if (intent.hasExtra("username_key")) {
            toDisplay = intent.getStringExtra("username_key");
        } else {
            toDisplay = SharedPrefUtils.getUsername(this);
        }

        receiver_msg.setText(String.format("Logged in with: %s", toDisplay));
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
}
