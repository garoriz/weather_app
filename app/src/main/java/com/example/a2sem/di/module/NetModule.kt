package com.example.a2sem.di.module

import com.example.a2sem.data.api.Api
import com.example.a2sem.di.qualifier.ApiKeyInterceptor
import com.example.a2sem.di.qualifier.MetricInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
private const val API_KEY = "91c36e69645ddfad20fe5f6734282966"
private const val QUERY_API_KEY = "appid"
private const val QUERY_METRIC = "metric"
private const val QUERY_UNITS = "units"

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Provides
    @ApiKeyInterceptor
    fun provideApiKeyInterceptor() = Interceptor { chain ->
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

    @Provides
    @MetricInterceptor
    fun provideMetricInterceptor() = Interceptor { chain ->
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

    @Provides
    fun getOkHttp(
        @ApiKeyInterceptor provideApiKeyInterceptor: Interceptor,
        @MetricInterceptor provideMetricInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(provideApiKeyInterceptor)
            .addInterceptor(provideMetricInterceptor)
            .build()

    @Provides
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideApi(
        provideOkHttpClient: OkHttpClient,
        provideGsonConverterFactory: GsonConverterFactory,
    ): Api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttpClient)
        .addConverterFactory(provideGsonConverterFactory)
        .build()
        .create(Api::class.java)
}
