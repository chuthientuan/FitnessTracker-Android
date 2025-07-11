package com.example.fitnesstracker.data.repository

import com.example.fitnesstracker.data.model.User
import com.example.fitnesstracker.data.model.UserOnboarding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun saveUserProfile(user: User, listener: OnCompleteListener<Void?>) {
        val userId = firebaseAuth.currentUser!!.uid
        firestore.collection("users")
            .document(userId)
            .set(user)
            .addOnCompleteListener(listener)
    }

    fun saveOnboarding(onboarding: UserOnboarding, listener: OnCompleteListener<Void?>) {
        val userId = firebaseAuth.currentUser!!.uid
        firestore.collection("users")
            .document(userId)
            .collection("onboarding")
            .document("data")
            .set(onboarding)
            .addOnCompleteListener(listener)
    }

    fun getUserProfile(userId: String, listener: OnCompleteListener<DocumentSnapshot?>) {
        firestore.collection("users")
            .document(userId)
            .get()
            .addOnCompleteListener(listener)
    }
}
