package com.example.fitnesstracker.utils;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseUtil {
    public static String currentUserId() {
        return FirebaseAuth.getInstance().getUid();
    }

    public static void logOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public static boolean isUserLoggedIn() {
        if (currentUserId() != null) {
            return true;
        }
        return false;
    }
}
