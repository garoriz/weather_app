package com.example.a2sem

import android.app.Application
import com.example.a2sem.di.AppComponent
import com.example.a2sem.di.DaggerAppComponent
import com.example.a2sem.di.module.AppModule
import com.example.a2sem.di.module.NetModule

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule())
            .netModule(NetModule())
            .build()
    }
}
