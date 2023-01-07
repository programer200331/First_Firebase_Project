package com.example.firstfirebaseproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firstfirebaseproject.R;
import com.example.firstfirebaseproject.controllers.FirebaseAuthController;
import com.example.firstfirebaseproject.databinding.ActivityMainBinding;
import com.example.firstfirebaseproject.interfaces.FirebaseProcessCallback;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private ActivityMainBinding mainBinding;
    private final FirebaseAuthController authController = FirebaseAuthController.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializedRecyclerView();
    }

    public void initializedRecyclerView() {
    }

    private void logout() {
        authController.signOut();
        performIntent(new Intent(getApplicationContext(), LoginActivity.class));
    }

    private void performIntent(Intent intent) {
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.log_out) {
            logout();
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}