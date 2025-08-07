package com.example.fitnesstracker.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.repository.FitnessRepository
import kotlinx.coroutines.launch

class MainViewModel(context: Context) : ViewModel() {
    private val fitnessRepository: FitnessRepository = FitnessRepository(context)
}