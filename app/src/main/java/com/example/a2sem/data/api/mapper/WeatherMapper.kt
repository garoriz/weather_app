package com.example.a2sem.data.api.mapper

import com.example.a2sem.data.api.response.weather.WeatherResponse
import com.example.a2sem.domain.entity.Weather

class WeatherMapper {
    fun map(response: WeatherResponse): Weather = Weather(
        id = response.id,
        city = response.name,
        country = response.sys.country,
        forecast = response.weather[0].main,
        temp = response.main.temp.toString() + "°C",
        humidity = response.main.humidity,
        temp_max = response.main.temp_max,
        temp_min = response.main.temp_min,
        url = "https://openweathermap.org/img/wn/" +
                response.weather[0].icon +
                "@2x.png",
        windDirection = getWindDirection(response.wind.deg)
    )

    private fun getWindDirection(deg: Int): String {
        when {
            deg > 270 -> {
                return "СЗ"
            }
            deg == 270 -> {
                return "З"
            }
            deg > 180 -> {
                return "ЮЗ"
            }
            deg == 180 -> {
                return "Ю"
            }
            deg >= 90 -> {
                return "ЮВ"
            }
            deg == 90 -> {
                return "В"
            }
            deg >= 0 -> {
                return "СВ"
            }
            else -> {
                return "С"
            }
        }
    }
}
