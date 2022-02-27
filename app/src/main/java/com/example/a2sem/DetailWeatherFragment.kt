package com.example.a2sem

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.a2sem.data.WeatherRepository
import com.example.a2sem.databinding.FragmentDetailWeatherBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DetailWeatherFragment : Fragment(R.layout.fragment_detail_weather) {

    private lateinit var binding: FragmentDetailWeatherBinding

    private val repository by lazy {
        WeatherRepository()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailWeatherBinding.bind(view)

        lifecycleScope.launch {
            val id = arguments?.getInt("id").toString()

            with(binding) {
                val weatherResponse = repository.getWeatherById(id)
                tvForecast.text = weatherResponse.weather.get(0).main
                val temp = weatherResponse.main.temp.toString() + "°C"
                tvTemp.text = temp
                tvCity.text = weatherResponse.name
                tvCountry.text = weatherResponse.sys.country
                tvHumidity.text = weatherResponse.main.humidity.toString()
                tvMinTemp.text = weatherResponse.main.temp_min.toString()
                tvMaxTemp.text = weatherResponse.main.temp_max.toString()
                val url = "https://openweathermap.org/img/wn/" +
                        weatherResponse.weather[0].icon +
                        "@2x.png"
                ivWeather.load(url)
                if (weatherResponse.wind.deg > 270) {
                    tvWindDirection.text = "СЗ"
                } else if (weatherResponse.wind.deg == 270) {
                    tvWindDirection.text = "З"
                } else if (weatherResponse.wind.deg > 180) {
                    tvWindDirection.text = "ЮЗ"
                } else if (weatherResponse.wind.deg == 180) {
                    tvWindDirection.text = "Ю"
                } else if (weatherResponse.wind.deg >= 90) {
                    tvWindDirection.text = "ЮВ"
                } else if (weatherResponse.wind.deg == 90) {
                    tvWindDirection.text = "В"
                } else if (weatherResponse.wind.deg >= 0) {
                    tvWindDirection.text = "СВ"
                } else {
                    tvWindDirection.text = "С"
                }
            }
        }
    }
}
