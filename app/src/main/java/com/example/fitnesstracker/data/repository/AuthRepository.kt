package com.example.fitnesstracker.data.repository

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun firebaseAuthWithGoogle(idToken: String?, userLiveData: MutableLiveData<FirebaseUser?>) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task: Task<AuthResult?>? ->
                if (task!!.isSuccessful) {
                    userLiveData.value = firebaseAuth.currentUser
                } else {
                    userLiveData.value = null
                }
            }
    }
}
