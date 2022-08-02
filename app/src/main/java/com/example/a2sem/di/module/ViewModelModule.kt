package com.example.a2sem.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a2sem.di.ViewModelKey
import com.example.a2sem.presentation.detailWeather.DetailWeatherViewModel
import com.example.a2sem.presentation.main.MainViewModel
import com.example.a2sem.utils.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(
        factory: AppViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(
        viewModel: MainViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailWeatherViewModel::class)
    fun bindDetailWeatherViewModel(
        viewModel: DetailWeatherViewModel
    ): ViewModel
}
