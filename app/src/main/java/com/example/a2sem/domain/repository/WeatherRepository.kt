package com.example.a2sem.domain.repository

import com.example.a2sem.data.api.response.cities.CitiesResponse
import com.example.a2sem.domain.entity.Weather

interface WeatherRepository {

    suspend fun getWeatherByName(city: String): Weather

    suspend fun getCities(lat: String, lon: String): CitiesResponse

    suspend fun getWeatherById(id: String): Weather
}
