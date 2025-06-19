package com.example.fitnesstracker.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnesstracker.data.repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<FirebaseUser> userLiveData = new MutableLiveData<>();
    private final AuthRepository authRepository = new AuthRepository();

    public void signInWithGoogle(String idToken) {
        authRepository.firebaseAuthWithGoogle(idToken, userLiveData);
    }

    public LiveData<FirebaseUser> getUser() {
        return userLiveData;
    }
}
