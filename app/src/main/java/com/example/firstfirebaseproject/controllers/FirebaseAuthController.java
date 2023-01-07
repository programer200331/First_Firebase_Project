package com.example.firstfirebaseproject.controllers;

import androidx.annotation.NonNull;

import com.example.firstfirebaseproject.interfaces.FirebaseProcessCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class FirebaseAuthController {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private static FirebaseAuthController instance;

    private FirebaseAuthController() {
    }

    public static synchronized FirebaseAuthController getInstance() {
        if (instance == null) {
            instance = new FirebaseAuthController();
        }
        return instance;
    }

    public void createAccount(String name, String email, String password, FirebaseProcessCallback callback) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    auth.getCurrentUser().sendEmailVerification();
                    UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build();
                    auth.getCurrentUser().updateProfile(request);
                    auth.signOut();
                    callback.onSuccess("Account created successfully");
                } else {
                    callback.onFailure(task.getException().getMessage());
                }
            }
        });
    }

    public void signIn(String email, String password, FirebaseProcessCallback callback) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (auth.getCurrentUser().isEmailVerified()) {
                        //TODO : Login success, Navigate to Home Screen (From UI)
                        callback.onSuccess("Login Successfully");
                    } else {
                        auth.signOut(); //TODO : Sorry you must be Verified Your Email
                        callback.onFailure("Please verify your email to login");
                    }

                } else {
                    callback.onFailure(task.getException().getMessage());
                }
            }
        });
    }

    public void forgotPassword(String email, FirebaseProcessCallback callback) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onSuccess("Reset email send successfully");
                } else {
                    callback.onFailure(task.getException().getMessage());
                }
            }
        });
    }

    public void signOut() {
        auth.signOut();
    }

    public boolean isSignedIn() {
        return auth.getCurrentUser() != null;
    }

}
