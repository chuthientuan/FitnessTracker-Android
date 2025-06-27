package com.example.fitnesstracker.data.repository;

import com.example.fitnesstracker.data.model.User;
import com.example.fitnesstracker.data.model.UserOnboarding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepository {
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public void saveUserProfile(User user, OnCompleteListener<Void> listener) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        firestore.collection("users")
                .document(userId)
                .set(user)
                .addOnCompleteListener(listener);
    }

    public void saveOnboarding(UserOnboarding onboarding, OnCompleteListener<Void> listener) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        firestore.collection("users")
                .document(userId)
                .collection("onboarding")
                .document("data")
                .set(onboarding)
                .addOnCompleteListener(listener);
    }
}
