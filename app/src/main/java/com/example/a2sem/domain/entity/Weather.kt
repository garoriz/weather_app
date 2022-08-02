package com.example.a2sem.domain.entity

data class Weather(
    val id: Int,
    val city: String,
    val country: String,
    val forecast: String,
    val temp: String,
    val humidity: Int,
    val temp_max: Double,
    val temp_min: Double,
    val url: String,
    val windDirection: String,
)
