package com.example.fitnesstracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesstracker.data.model.User
import com.example.fitnesstracker.data.repository.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot

class HomeViewModel : ViewModel() {
    private val userRepository = UserRepository()
    private val userLiveData = MutableLiveData<User?>()
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun fetchUserProfile() {
        val userId = firebaseAuth.currentUser!!.uid
        userRepository.getUserProfile(userId) { task: Task<DocumentSnapshot?>? ->
            if (task!!.isSuccessful) {
                val user = task.getResult()!!.toObject(User::class.java)
                userLiveData.value = user
            } else {
                userLiveData.value = null
            }
        }
    }

    fun getUserProfile(): LiveData<User?> {
        return userLiveData
    }
}
