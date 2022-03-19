package com.example.a2sem.di

import com.example.a2sem.data.WeatherRepositoryImpl
import com.example.a2sem.data.api.Api
import com.example.a2sem.data.api.mapper.WeatherMapper
import com.example.a2sem.domain.repository.WeatherRepository
import com.example.a2sem.domain.usecase.GetCitiesUseCase
import com.example.a2sem.domain.usecase.GetWeatherByIdUseCase
import com.example.a2sem.domain.usecase.GetWeatherByNameUseCase
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
private const val API_KEY = "91c36e69645ddfad20fe5f6734282966"
private const val QUERY_API_KEY = "appid"
private const val QUERY_METRIC = "metric"
private const val QUERY_UNITS = "units"

object DIContainer {

    private val apiKeyInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, API_KEY)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    private val metricInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(
                QUERY_UNITS,
                QUERY_METRIC
            )
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    private val okhttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(metricInterceptor)
            .build()
    }

    val api: Api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    private val weatherRepository: WeatherRepository = WeatherRepositoryImpl(
        api = api,
        weatherMapper = WeatherMapper()
    )

    val getWeatherByNameUseCase: GetWeatherByNameUseCase = GetWeatherByNameUseCase(
        weatherRepository = weatherRepository,
        dispatcher = Dispatchers.Default
    )

    val getCitiesUseCase: GetCitiesUseCase = GetCitiesUseCase(
        weatherRepository = weatherRepository,
        dispatcher = Dispatchers.Default
    )

    val getWeatherByIdUseCase: GetWeatherByIdUseCase = GetWeatherByIdUseCase(
        weatherRepository = weatherRepository,
        dispatcher = Dispatchers.Default
    )
}
