package com.example.fitnesstracker.utils

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseUtil {
    fun currentUserId(): String? {
        return FirebaseAuth.getInstance().uid
    }

    fun logOut() {
        FirebaseAuth.getInstance().signOut()
    }

    val isUserLoggedIn: Boolean
        get() = currentUserId() != null

    fun checkProfileExists(listener: OnProfileCheckListener) {
        val userId = currentUserId()
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId!!)
            .get()
            .addOnSuccessListener(OnSuccessListener { documentSnapshot: DocumentSnapshot? ->
                val hasUserId = documentSnapshot!!.contains("userId")
                listener.onResult(hasUserId)
            })
            .addOnFailureListener { e: Exception? ->
                listener.onResult(false)
            }
    }

    fun interface OnProfileCheckListener {
        fun onResult(exists: Boolean)
    }
}
