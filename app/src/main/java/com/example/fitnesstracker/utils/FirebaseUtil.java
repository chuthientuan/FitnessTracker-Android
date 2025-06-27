package com.example.fitnesstracker.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseUtil {
    public static String currentUserId() {
        return FirebaseAuth.getInstance().getUid();
    }

    public static void logOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public static boolean isUserLoggedIn() {
        return currentUserId() != null;
    }

    public static void checkProfileExists(OnProfileCheckListener listener) {
        String userId = currentUserId();
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    boolean hasUserId = documentSnapshot.contains("userId");
                    listener.onResult(hasUserId);
                })
                .addOnFailureListener(e -> {
                    listener.onResult(false);
                });
    }

    public interface OnProfileCheckListener {
        void onResult(boolean exists);
    }

}
