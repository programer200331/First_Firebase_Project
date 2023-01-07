package com.example.firstfirebaseproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstfirebaseproject.controllers.FirebaseAuthController;
import com.example.firstfirebaseproject.databinding.ActivitySplashActivityBinding;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ActivitySplashActivityBinding.inflate(getLayoutInflater())
                .getRoot());
    }


    @Override
    protected void onStart() {
        super.onStart();
        handelSplashActivity();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void handelSplashActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isSignedIn = FirebaseAuthController.getInstance().isSignedIn();
                Intent intent;
                if (isSignedIn) {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                }
                startActivity(intent);
            }
        }, 3000);

    }

}