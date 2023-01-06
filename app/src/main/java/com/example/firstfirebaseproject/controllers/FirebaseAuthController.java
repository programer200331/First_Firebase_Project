package com.example.firstfirebaseproject.controllers;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthController {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    public void createAccount(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            auth.getCurrentUser().sendEmailVerification();
                            auth.signOut();
                        } else {

                        }
                    }
                });
    }

    public void signIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            if (auth.getCurrentUser().isEmailVerified()) {

                                //TODO : Login success, Navigate to Home Screen (From UI)

                            } else {
                                auth.signOut(); //TODO : Sorry you must be Verified Your Email
                            }

                        } else {

                        }

                    }
                });
    }

    public void signOut() {

        auth.signOut();

    }

    public void forgotPassword(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {


                        } else {

                        }

                    }
                });

    }

    public boolean isSignedIn() {
        return auth.getCurrentUser() != null;
    }

}
