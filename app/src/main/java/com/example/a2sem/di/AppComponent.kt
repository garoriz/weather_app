package com.example.a2sem.di

import com.example.a2sem.di.module.AppModule
import com.example.a2sem.di.module.NetModule
import com.example.a2sem.di.module.RepoModule
import com.example.a2sem.di.module.ViewModelModule
import com.example.a2sem.presentation.MainActivity
import com.example.a2sem.presentation.detailWeather.DetailWeatherFragment
import com.example.a2sem.presentation.main.MainFragment
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        NetModule::class,
        RepoModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(mainFragment: MainFragment)

    fun inject(detailWeatherFragment: DetailWeatherFragment)
}
