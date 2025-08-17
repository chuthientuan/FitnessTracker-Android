package com.example.fitnesstracker.data.repository

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.records.metadata.Device
import androidx.health.connect.client.records.metadata.Metadata
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.kilocalories
import androidx.health.connect.client.units.kilometers
import androidx.health.connect.client.units.meters
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnesstracker.data.model.FitnessData
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime

class FitnessRepository(
    private val healthConnectClient: HealthConnectClient
) {
    private val fitnessData = MutableLiveData<FitnessData>()

    fun getFitnessData(): LiveData<FitnessData> = fitnessData

    suspend fun aggregateStep() {
        try {
            val startTime =
                ZonedDateTime.now().toLocalDate().atStartOfDay(ZonedDateTime.now().zone).toInstant()
            val endTime = Instant.now()

            val response = healthConnectClient.aggregate(
                AggregateRequest(
                    metrics = setOf(StepsRecord.COUNT_TOTAL),
                    timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
                )
            )
            val totalSteps = response[StepsRecord.COUNT_TOTAL] ?: 0L
            val currentData = fitnessData.value ?: FitnessData()
            fitnessData.postValue(currentData.copy(steps = totalSteps.toInt()))
        } catch (e: Exception) {
            Log.e(TAG, "aggregateStep: $e")
        }
    }

    suspend fun insertTestSteps(steps: Int = 1000) {
        try {
            val endTime = Instant.now()
            val startTime = endTime.minusSeconds(3600) // 1 giờ trước

            val stepsRecord = StepsRecord(
                count = steps.toLong(),
                startTime = startTime,
                endTime = endTime,
                startZoneOffset = ZoneOffset.UTC,
                endZoneOffset = ZoneOffset.UTC,
                metadata = Metadata.autoRecorded(
                    device = Device(type = Device.TYPE_WATCH)
                )
            )

            healthConnectClient.insertRecords(listOf(stepsRecord))
            Log.d(TAG, "insertTestSteps: Đã chèn $steps bước vào Health Connect")
        } catch (e: Exception) {
            Log.e(TAG, "insertTestSteps: $e")
        }
    }

    suspend fun insertTestCalories(calories: Double = 100.0) {
        try {
            val endTime = Instant.now()
            val startTime = endTime.minusSeconds(3600) // 1 giờ trước
            val caloriesRecord = TotalCaloriesBurnedRecord(
                energy = calories.kilocalories,
                startTime = startTime,
                endTime = endTime,
                startZoneOffset = ZoneOffset.UTC,
                endZoneOffset = ZoneOffset.UTC,
                metadata = Metadata.autoRecorded(
                    device = Device(type = Device.TYPE_WATCH)
                )
            )
            healthConnectClient.insertRecords(listOf(caloriesRecord))
        } catch (e: Exception) {
            Log.e(TAG, "insertTestCalories: $e")
        }
    }

    @SuppressLint("DefaultLocale")
    suspend fun readTotalCaloriesBurned() {
        try {
            val startTime =
                ZonedDateTime.now().toLocalDate().atStartOfDay(ZonedDateTime.now().zone).toInstant()
            val endTime = Instant.now()
            val response = healthConnectClient.aggregate(
                AggregateRequest(
                    metrics = setOf(TotalCaloriesBurnedRecord.ENERGY_TOTAL),
                    timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
                )
            )
            val totalCalories = response[TotalCaloriesBurnedRecord.ENERGY_TOTAL]?.inKilocalories
            val formattedCalories = String.format("%.3f", totalCalories ?: 0.0).toDouble()
            val currentData = fitnessData.value ?: FitnessData()
            fitnessData.postValue(currentData.copy(calories = formattedCalories))
        } catch (e: Exception) {
            Log.e(TAG, "readTotalCaloriesBurned: $e")
        }
    }

    suspend fun insertDistance(distance: Double) {
        try {
            val endTime = Instant.now()
            val startTime = endTime.minusSeconds(3600)
            val distanceRecord = DistanceRecord(
                distance = distance.kilometers,
                startTime = startTime,
                endTime = endTime,
                startZoneOffset = ZoneOffset.UTC,
                endZoneOffset = ZoneOffset.UTC,
                metadata = Metadata.autoRecorded(
                    device = Device(type = Device.TYPE_WATCH)
                )
            )
            healthConnectClient.insertRecords(listOf(distanceRecord))
        } catch (e: Exception) {
            Log.e(TAG, "insertDistance: $e")
        }
    }

    @SuppressLint("DefaultLocale")
    suspend fun readDistance() {
        try {
            val startTime =
                ZonedDateTime.now().toLocalDate().atStartOfDay(ZonedDateTime.now().zone).toInstant()
            val endTime = Instant.now()
            val response = healthConnectClient.aggregate(
                AggregateRequest(
                    metrics = setOf(DistanceRecord.DISTANCE_TOTAL),
                    timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
                )
            )
            val totalDistance = response[DistanceRecord.DISTANCE_TOTAL]?.inKilometers
            val formattedDistance = String.format("%.2f", totalDistance ?: 0.0).toDouble()
            val currentData = fitnessData.value ?: FitnessData()
            fitnessData.postValue(currentData.copy(distance = formattedDistance))
        } catch (e: Exception) {
            Log.e(TAG, "readDistance: $e")
        }
    }
}
