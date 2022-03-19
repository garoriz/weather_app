package com.example.a2sem.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a2sem.R
import com.example.a2sem.presentation.main.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, MainFragment())
            .commit()
    }
}
