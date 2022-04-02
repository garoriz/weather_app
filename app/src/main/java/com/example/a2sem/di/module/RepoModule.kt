package com.example.a2sem.di.module

import com.example.a2sem.data.WeatherRepositoryImpl
import com.example.a2sem.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
interface RepoModule {

    @Binds
    fun weatherRepository(
        impl: WeatherRepositoryImpl
    ): WeatherRepository
}
