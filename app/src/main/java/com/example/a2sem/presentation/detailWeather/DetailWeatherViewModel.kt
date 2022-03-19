package com.example.a2sem.presentation.detailWeather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2sem.domain.entity.Weather
import com.example.a2sem.domain.usecase.GetWeatherByIdUseCase
import kotlinx.coroutines.launch

class DetailWeatherViewModel(
    private val getWeatherByIdUseCase: GetWeatherByIdUseCase
) : ViewModel() {

    private var _weather: MutableLiveData<Result<Weather>> = MutableLiveData()
    val weather: LiveData<Result<Weather>> = _weather

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun onGetWeather(id: String) {
        viewModelScope.launch {
            try {
                val weather = getWeatherByIdUseCase(id)
                _weather.value = Result.success(weather)
            } catch (ex: Exception) {
                _weather.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }
}
