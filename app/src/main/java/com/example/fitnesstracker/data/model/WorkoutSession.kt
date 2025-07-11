package com.example.fitnesstracker.data.model

data class WorkoutSession(
    var sessionId: String?,
    var uid: String?,
    var type: String?,
    var distanceKm: Double,
    var durationMinutes: Int,
    var startTime: Long,
    var endTime: Long,
    var route: MutableList<LatLng?>?
)
