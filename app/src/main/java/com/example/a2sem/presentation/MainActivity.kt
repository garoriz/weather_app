package com.example.a2sem.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a2sem.App
import com.example.a2sem.R
import com.example.a2sem.di.AppComponent
import com.example.a2sem.presentation.main.MainFragment

class MainActivity : AppCompatActivity() {

    lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent = (application as App).appComponent
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, MainFragment())
            .commit()
    }
}
