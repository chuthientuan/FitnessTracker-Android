package com.example.fitnesstracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.model.FitnessData
import com.example.fitnesstracker.data.repository.FitnessRepository
import kotlinx.coroutines.launch

class MainViewModel(private val fitnessRepository: FitnessRepository) : ViewModel() {
    val fitnessData: LiveData<FitnessData> = fitnessRepository.getFitnessData()

    fun readFitnessData() {
        viewModelScope.launch {
            fitnessRepository.aggregateStep()
            fitnessRepository.readTotalCaloriesBurned()
            fitnessRepository.readDistance()
        }
    }

    fun insertDataTest() {
        viewModelScope.launch {
            fitnessRepository.insertTestSteps(steps = 1200)
            fitnessRepository.insertTestCalories(calories = 100.0)
            fitnessRepository.insertDistance(distance = 10.0)
        }
    }
}
class MainViewModelFactory(private val repository: FitnessRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}