package com.example.a2sem.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.a2sem.R
import com.example.a2sem.data.WeatherRepositoryImpl
import com.example.a2sem.data.api.mapper.WeatherMapper
import com.example.a2sem.databinding.FragmentDetailWeatherBinding
import com.example.a2sem.di.DIContainer
import com.example.a2sem.domain.usecase.GetWeatherByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class DetailWeatherFragment : Fragment(R.layout.fragment_detail_weather) {

    private lateinit var binding: FragmentDetailWeatherBinding
    private lateinit var getWeatherByIdUseCase: GetWeatherByIdUseCase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailWeatherBinding.bind(view)
        initObject()

        lifecycleScope.launch {
            val id = arguments?.getInt("id").toString()

            with(binding) {
                val weather = getWeatherByIdUseCase(id)
                tvForecast.text = weather.forecast
                tvTemp.text = weather.temp
                tvCity.text = weather.city
                tvCountry.text = weather.country
                tvHumidity.text = weather.humidity.toString()
                tvMinTemp.text = weather.temp_min.toString()
                tvMaxTemp.text = weather.temp_max.toString()
                ivWeather.load(weather.url)
                tvWindDirection.text = weather.windDirection
            }
        }
    }

    private fun initObject() {
        getWeatherByIdUseCase = GetWeatherByIdUseCase(
            weatherRepository = WeatherRepositoryImpl(
                api = DIContainer.api,
                weatherMapper = WeatherMapper()
            ),
            dispatcher = Dispatchers.Default
        )
    }
}
