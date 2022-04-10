package com.example.a2sem.presentation.detailWeather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2sem.domain.entity.Weather
import com.example.a2sem.domain.usecase.GetWeatherByIdUseCase
import com.example.a2sem.domain.usecase.GetWeatherByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailWeatherViewModel @Inject constructor(
    private val getWeatherByIdUseCase: GetWeatherByIdUseCase,
    private val getWeatherByNameUseCase: GetWeatherByNameUseCase
) : ViewModel() {

    private var _weatherId: MutableLiveData<Result<Int>> = MutableLiveData()
    val weatherId: LiveData<Result<Int>> = _weatherId

    private var _weather: MutableLiveData<Result<Weather>> = MutableLiveData()
    val weather: LiveData<Result<Weather>> = _weather

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun onGetWeatherId(city: String) {
        viewModelScope.launch {
            try {
                val weatherId = getWeatherByNameUseCase(city).id
                _weatherId.value = Result.success(weatherId)
            } catch (ex: Exception) {
                _weather.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

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
