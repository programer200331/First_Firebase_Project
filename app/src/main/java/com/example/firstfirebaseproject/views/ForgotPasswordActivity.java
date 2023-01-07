package com.example.firstfirebaseproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstfirebaseproject.R;
import com.example.firstfirebaseproject.controllers.FirebaseAuthController;
import com.example.firstfirebaseproject.databinding.ActivityForgotPasswordBinding;
import com.example.firstfirebaseproject.interfaces.FirebaseProcessCallback;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        binding.butSend.setOnClickListener(this::onClick);
    }

    private void performSendResetEmail() {
        if (checkData()) {
            forgotPassword();
        } else {
            Toast.makeText(this, R.string.toast_enter_email_to_continue, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkData() {
        return !binding.editEmail.getText().toString().isEmpty();
    }

    private void forgotPassword() {
        FirebaseAuthController.getInstance().forgotPassword(binding.editEmail.getText().toString()
                , new FirebaseProcessCallback() {
                    @Override
                    public void onSuccess(String message) {
                        Toast.makeText(ForgotPasswordActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(ForgotPasswordActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.but_send) {
            performSendResetEmail();
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