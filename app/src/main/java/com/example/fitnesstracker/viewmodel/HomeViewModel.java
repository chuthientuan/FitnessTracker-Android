package com.example.fitnesstracker.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnesstracker.data.model.User;
import com.example.fitnesstracker.data.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;

public class HomeViewModel extends ViewModel {
    private final UserRepository userRepository = new UserRepository();
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public void fetchUserProfile() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        userRepository.getUserProfile(userId, task -> {
            if (task.isSuccessful()) {
                User user = task.getResult().toObject(User.class);
                userLiveData.setValue(user);
            } else {
                userLiveData.setValue(null);
            }
        });
    }

    public LiveData<User> getUserProfile() {
        return userLiveData;
    }
}
