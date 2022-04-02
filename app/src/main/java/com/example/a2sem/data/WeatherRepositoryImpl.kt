package com.example.a2sem.data

import com.example.a2sem.data.api.Api
import com.example.a2sem.data.api.mapper.WeatherMapper
import com.example.a2sem.data.api.response.cities.CitiesResponse
import com.example.a2sem.domain.entity.Weather
import com.example.a2sem.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: Api,
    private val weatherMapper: WeatherMapper,
) : WeatherRepository {
    override suspend fun getWeatherByName(city: String): Weather {
        return weatherMapper.map(api.getWeatherByName(city))
    }

    override suspend fun getCities(lat: String, lon: String): CitiesResponse {
        return api.getCities(lat, lon)
    }

    override suspend fun getWeatherById(id: String): Weather {
        return weatherMapper.map(api.getWeatherById(id))
    }
}
