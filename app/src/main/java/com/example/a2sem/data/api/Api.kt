package com.example.a2sem.data.api

import com.example.a2sem.data.api.response.cities.CitiesResponse
import com.example.a2sem.data.api.response.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("weather?lang=RU")
    suspend fun getWeatherByName(@Query("q") city: String): WeatherResponse

    @GET("find?cnt=10")
    suspend fun getCities(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): CitiesResponse

    @GET("weather?lang=RU")
    suspend fun getWeatherById(@Query("id") id: String): WeatherResponse
}
