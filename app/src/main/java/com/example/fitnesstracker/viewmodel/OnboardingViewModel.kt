package com.example.fitnesstracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesstracker.data.model.User
import com.example.fitnesstracker.data.model.UserOnboarding
import com.example.fitnesstracker.data.repository.UserRepository
import com.google.android.gms.tasks.Task

class OnboardingViewModel : ViewModel() {
    val isSaved: MutableLiveData<Boolean?> = MutableLiveData<Boolean?>()
    private val repository = UserRepository()

    fun submitOnboarding(
        experience: String?,
        frequency: String?,
        runningGoal: String?,
        trainingEvent: String?
    ) {
        val onboarding = UserOnboarding(experience, frequency, runningGoal, trainingEvent)
        repository.saveOnboarding(onboarding) { task: Task<Void?>? ->
            isSaved.value = task!!.isSuccessful
        }
    }

    fun submitUserProfile(
        userName: String?,
        userId: String?,
        email: String?,
        gender: String?,
        height: Double,
        weight: Double
    ) {
        val profile = User(userName, userId, email, gender, height, weight)
        repository.saveUserProfile(profile) { task: Task<Void?>? ->
            isSaved.value = task!!.isSuccessful
        }
    }

    val getIsSaved: MutableLiveData<Boolean?> = isSaved
}
