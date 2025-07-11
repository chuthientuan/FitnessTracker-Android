package com.example.fitnesstracker.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesstracker.utils.FirebaseUtil
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel : ViewModel() {
    private val userLiveData = MutableLiveData<FirebaseUser?>()
    private val signOutSuccess = MutableLiveData<Boolean?>()
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun signInWithGoogle(idToken: String?) {
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

    fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task: Task<AuthResult?>? ->
                if (task!!.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    Log.d(
                        "FB_LOGIN",
                        "Firebase sign in success: " + (user?.email)
                    )
                    userLiveData.value = user
                } else {
                    Log.e("FB_LOGIN", "Firebase sign in failed", task.exception)
                    userLiveData.value = null
                }
            }
    }

    fun signInWithEmailAndPassword(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult?>? ->
                if (task!!.isSuccessful) {
                    userLiveData.value = firebaseAuth.currentUser
                } else {
                    userLiveData.value = null
                }
            }
    }

    val getUser: LiveData<FirebaseUser?>
        get() = userLiveData

    fun signOut() {
        FirebaseUtil.logOut()
        LoginManager.getInstance().logOut()
        signOutSuccess.value = true
    }

    val signOutResult: LiveData<Boolean?>
        get() = signOutSuccess
}
