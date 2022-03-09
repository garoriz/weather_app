package com.example.a2sem.domain.usecase

import com.example.a2sem.domain.entity.Weather
import com.example.a2sem.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetWeatherByNameUseCase(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main,
) {
    suspend operator fun invoke(city: String): Weather {
        return withContext(dispatcher) {
            weatherRepository.getWeatherByName(city)
        }
    }
}
