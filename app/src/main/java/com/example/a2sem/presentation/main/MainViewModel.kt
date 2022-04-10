package com.example.a2sem.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2sem.data.api.response.cities.CitiesResponse
import com.example.a2sem.domain.usecase.GetCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase
) : ViewModel() {

    private var _cities: MutableLiveData<Result<CitiesResponse>> = MutableLiveData()
    val cities: LiveData<Result<CitiesResponse>> = _cities

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun onGetCities(lat: String, lon: String) {
        viewModelScope.launch {
            try {
                val cities = getCitiesUseCase(lat, lon)
                _cities.value = Result.success(cities)
            } catch (ex: Exception) {
                _cities.value = Result.failure(ex)

                _error.value = ex
            }
        }
    }
}
