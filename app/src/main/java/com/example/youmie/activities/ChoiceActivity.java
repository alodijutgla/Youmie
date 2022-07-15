package com.example.youmie.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.youmie.R;
import com.example.youmie.utils.DatabaseUtils;
import com.example.youmie.utils.SharedPrefUtils;

public class ChoiceActivity extends AppCompatActivity {

    TextView receiver_msg;
    Button hostButton, guestButton;
    String username;
    DatabaseUtils databaseUtils;

    /**
     * onCreate() method initializes the DatabaseUtils, displays the username value in an editText
     * and calls the redirectToHostPageOnClick() when the host button is clicked and the
     * redirectToGuestPageOnClick() when the guest button is clicked
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        databaseUtils = new DatabaseUtils(this);

        displayUsername();
        redirectToHostPageOnClick();
        redirectToGuestPageOnClick();
    }

    /**
     * onBackPressed() defines the action when the back button is pressed.
     * In this case we want to ensure that the activity finished when this event happens
     */
    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * onCreateOptionsMenu() creates a three dot menu in the top right corner of the activity
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // it renders the the menu created in the menu.xml file
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * onOptionsItemSelected() defines the behaviour for every action in the menu.
     * For Logout it will log the user out and bring to the LogInActivity.
     * For Delete my user it will invoke a confirmation dialogue and delete the user if it is selected
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.log_out) {
            // if logout is selected the Shared Preferences are set to empty
            SharedPrefUtils.savePassword("", this);
            SharedPrefUtils.saveUsername("", this);
            // redirect to the LogInActivity
            startActivity(new Intent(this, LogInActivity.class));
            return true;
        }
        if (item.getItemId() == R.id.delete) {
            // if delete is selected we invoke the confirmation dialogue to avoid unintentional clicking
            createConfirmationDialogue();
            return true;
        }
        throw new IllegalStateException("Unexpected value: " + item.getItemId());
    }

    /**
     * displayUsername() it displays the name of the user at the top using the TextView
     */
    private void displayUsername() {
        // get the adress of the TextView to put the username
        receiver_msg = (TextView) findViewById(R.id.received_value);
        Intent intent = getIntent();
        if (intent.hasExtra("username_key")) {
            // if the username comes from the previous activity via the intent we use it
            username = intent.getStringExtra("username_key");
        } else {
            // if we don't have the username from the previous activity, we get it from the Shared Preferences
            username = SharedPrefUtils.getUsername(this);
        }
        receiver_msg.setText(String.format("Hello %s", username));
    }

    /**
     * redirectToHostPageOnClick() starts the HostActivity if the user presses the Host button
     */
    private void redirectToHostPageOnClick() {
        hostButton = (Button) findViewById(R.id.hostBtn);
        hostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HostActivity.class));
            }
        });
    }

    /**
     * redirectToGuestPageOnClick() starts the GuestActivity if the user presses the Guest button
     */
    private void redirectToGuestPageOnClick() {
        guestButton = (Button) findViewById(R.id.guestBtn);
        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GuestActivity.class));
            }
        });
    }

    /**
     * createConfirmationDialogue() it prompts a confirmation dialogue with the options
     * `Cancel` and `Confirm`. If Confirm is selected, the user will be deleted with all the Host entries
     */
    private void createConfirmationDialogue() {
        // created new AlertDialogue Builder with all the parameters to create the confirmation dialogue
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete your user?")
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (databaseUtils.deleteUserHostEntries(username)) {
                            // if the user is deleted the Shared Preferences are set to empty
                            SharedPrefUtils.savePassword("", getApplicationContext());
                            SharedPrefUtils.saveUsername("", getApplicationContext());
                            startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }
}
