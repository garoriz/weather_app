package com.example.a2sem.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a2sem.di.DIContainer
import com.example.a2sem.presentation.detailWeather.DetailWeatherViewModel
import com.example.a2sem.presentation.main.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val di: DIContainer,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(di.getCitiesUseCase)
                        as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            modelClass.isAssignableFrom(DetailWeatherViewModel::class.java) ->
                DetailWeatherViewModel(di.getWeatherByIdUseCase)
                        as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        }
}
