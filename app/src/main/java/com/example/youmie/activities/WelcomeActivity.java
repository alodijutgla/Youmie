package com.example.youmie.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.youmie.R;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {

    Timer timer;

    /**
     * onCreate() calls the methods runAnimation() with the imageView provided and then the
     * startLoginActivity() is called ensuring the animation is finished before switching to the next activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        runAnimation((ImageView) findViewById(R.id.imageTitle));
        startLoginActivity();
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
     * startLoginActivity() delays the redirection to the LogIn activity by 4 seconds so
     * it asserts that the animation is finished before changing to the new activity
     */
    private void startLoginActivity() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // creates a new Intent object and starts the new activity with it
                startActivity(new Intent(WelcomeActivity.this, LogInActivity.class));
                finish();
            }
        }, 4000);
    }

    /**
     * runAnimation(ImageView imageView) creates an animation for the provided ImageView object
     * which in this case is the Youmie title. It creates a fade_in for 1.5s and a fade_out for 1.5s
     */
    private void runAnimation(ImageView imageView) {
        // it scales the view object provided from 0 to 1 in x and y providing the fade in effect
        ScaleAnimation fade_in = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        fade_in.setDuration(1500);

        // it scales the view object provided from 1 to 0 in x and y providing the fade out effect
        ScaleAnimation fade_out = new ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        fade_out.setDuration(1500);

        // it starts the animation
        imageView.startAnimation(fade_in);
        // it creates an animationListener so the two animations happen sequentially one after the other
        fade_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // when fade_in animation ends it starts the fade_out animation
                imageView.startAnimation(fade_out);
            }
        });
    }
}