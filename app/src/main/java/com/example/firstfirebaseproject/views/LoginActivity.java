package com.example.firstfirebaseproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstfirebaseproject.R;
import com.example.firstfirebaseproject.controllers.FirebaseAuthController;
import com.example.firstfirebaseproject.databinding.ActivityLoginBinding;
import com.example.firstfirebaseproject.interfaces.FirebaseProcessCallback;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding loginBinding;
    private final FirebaseAuthController authController = FirebaseAuthController.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        setOnClickListener();
    }

    private void setOnClickListener() {
        loginBinding.butLogin.setOnClickListener(this);
        loginBinding.tvCreateNow.setOnClickListener(this);
        loginBinding.tvForgotPassword.setOnClickListener(this);
    }

    private void performLogin() {
        if (checkData()) {
            login();
        }
    }

    private boolean checkData() {
        if (!Objects.requireNonNull(loginBinding.loginEmail.getText()).toString().isEmpty()) {
            if (!Objects.requireNonNull(loginBinding.loginPassword.getText()).toString().isEmpty()) {
                return true;
            } else {
                Toast.makeText(this, R.string.toast_enter_password, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.toast_enter_email, Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    private void login() {
        authController.signIn(Objects.requireNonNull(loginBinding.loginEmail.getText()).toString(), Objects.requireNonNull(loginBinding.loginPassword.getText()).toString(), new FirebaseProcessCallback() {
            @Override
            public void onSuccess(String message) {
                goToMainActivity();
                Toast.makeText(LoginActivity.this, "" + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(loginBinding.getRoot(), "" + message, BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    private void goToMainActivity() {
        Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentMain);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.but_login) {
            performLogin();
        } else if (view.getId() == R.id.tv_forgot_password) {
            startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
        } else {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

}