package com.example.fitnesstracker.data.model

data class User(
    var userName: String? = null,
    var userId: String? = null,
    var email: String? = null,
    var gender: String? = null,
    var height: Double = 0.0,
    var weight: Double = 0.0
)
