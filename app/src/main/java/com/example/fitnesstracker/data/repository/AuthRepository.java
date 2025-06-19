package com.example.fitnesstracker.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthRepository {
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public void firebaseAuthWithGoogle(String idToken, MutableLiveData<FirebaseUser> userLiveData) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                   if (task.isSuccessful()) {
                       userLiveData.setValue(firebaseAuth.getCurrentUser());
                   } else {
                       userLiveData.setValue(null);
                   }
                });
    }
}
