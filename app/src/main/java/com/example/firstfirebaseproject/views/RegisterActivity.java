package com.example.firstfirebaseproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstfirebaseproject.R;
import com.example.firstfirebaseproject.controllers.FirebaseAuthController;
import com.example.firstfirebaseproject.databinding.ActivityRegisterBinding;
import com.example.firstfirebaseproject.interfaces.FirebaseProcessCallback;
import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityRegisterBinding binding;
    private final FirebaseAuthController authController = FirebaseAuthController.getInstance();
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializedView();
    }

    private void initializedView() {
        controllerGenderSelection();
        setOnClickListener();
    }

    private void setOnClickListener() {
        binding.butRegister.setOnClickListener(this);
    }

    private void performRegister() {
        if (checkData()) {
            createAccount();
        }
    }

    private boolean checkData() {
        if (!binding.etRUserName.getText().toString().isEmpty()
                && !binding.etRUserEmail.getText().toString().isEmpty()
                && !binding.etRUserPassword.getText().toString().isEmpty()
                && gender != null) {
            return true;
        }
        Snackbar.make(binding.getRoot(), R.string.toast_enter_data, Snackbar.LENGTH_SHORT).show();
        return false;
    }


    private void createAccount() {
        authController.createAccount(binding.etRUserName.getText().toString()
                , binding.etRUserEmail.getText().toString()
                , binding.etRUserPassword.getText().toString()
                , new FirebaseProcessCallback() {
                    @Override
                    public void onSuccess(String message) {
                        Toast.makeText(RegisterActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                        goToMainActivity();
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(RegisterActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void goToMainActivity() {
        Intent intentMain = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intentMain);
    }

    private void controllerGenderSelection() {
        binding.radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                gender = i == R.id.radio_male ? "M" : "F";
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.but_register) {
            performRegister();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

}