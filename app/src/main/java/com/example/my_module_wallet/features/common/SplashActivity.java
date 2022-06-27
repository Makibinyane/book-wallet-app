package com.example.my_module_wallet.features.common;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.my_module_wallet.R;
import com.example.my_module_wallet.features.user.ui.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    ProgressBar splashProgress;
    int SPLASH_TIME = 3000; //This is 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashProgress = findViewById(R.id.splashProgress);
        playProgress();
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do any action here. Now we are moving to next page
                Intent mySuperIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(mySuperIntent);
                finish();

            }
        }, SPLASH_TIME);
    }

    //Method to run progress bar for 5 seconds
    private void playProgress() {
        ObjectAnimator.ofInt(splashProgress, "progress", 100)
                .setDuration(5000)
                .start();
    }
}