package com.example.a2sem.domain.usecase

import com.example.a2sem.data.api.response.cities.CitiesResponse
import com.example.a2sem.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCitiesUseCase(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    suspend operator fun invoke(lat: String, lon: String): CitiesResponse {
        return withContext(dispatcher) {
            weatherRepository.getCities(lat, lon)
        }
    }
}
