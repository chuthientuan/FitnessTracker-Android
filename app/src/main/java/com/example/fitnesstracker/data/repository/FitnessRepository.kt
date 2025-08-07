package com.example.fitnesstracker.data.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnesstracker.data.model.FitnessData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneId

class FitnessRepository(private val context: Context) {
    private lateinit var healthConnectClient: HealthConnectClient
    private val fitnessData = MutableLiveData<FitnessData>()

    fun getFitnessData(): LiveData<FitnessData> = fitnessData

    suspend fun readSteps() {
        // Đọc dữ liệu trong IO dispatcher
        withContext(Dispatchers.IO) {
            try {
                val endOfDay = LocalDateTime.now()
                val startOfDay = endOfDay.toLocalDate().atStartOfDay()

                val startTime = startOfDay.atZone(ZoneId.systemDefault()).toInstant()
                val endTime = endOfDay.atZone(ZoneId.systemDefault()).toInstant()

                val readRequest = ReadRecordsRequest(
                    recordType = StepsRecord::class, // Sử dụng KClass trực tiếp trong Kotlin
                    timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
                )

                val response = healthConnectClient.readRecords(readRequest)
                var totalSteps = 0L // Long vì StepsRecord.steps là Long
                response.records.forEach { record ->
                    totalSteps += record.count
                }

                Log.d(TAG, "Total steps today (Health Connect): $totalSteps")
                fitnessData.postValue(FitnessData(totalSteps.toInt())) // Chuyển đổi về Int
            } catch (e: Exception) {
                Log.e(TAG, "Failed to read steps from Health Connect: ${e.message}")
                fitnessData.postValue(FitnessData(0))
            }
        }
    }
}
