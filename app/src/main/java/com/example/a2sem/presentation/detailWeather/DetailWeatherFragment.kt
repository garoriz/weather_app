package com.example.a2sem.presentation.detailWeather

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.a2sem.R
import com.example.a2sem.databinding.FragmentDetailWeatherBinding
import com.example.a2sem.di.DIContainer
import com.example.a2sem.utils.ViewModelFactory

class DetailWeatherFragment : Fragment(R.layout.fragment_detail_weather) {

    private lateinit var binding: FragmentDetailWeatherBinding
    private lateinit var viewModel: DetailWeatherViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailWeatherBinding.bind(view)

        initObject()
        initObservers()

        val id = arguments?.getInt("id").toString()
        viewModel.onGetWeather(id)
    }

    private fun initObject() {
        val factory = ViewModelFactory(DIContainer)
        viewModel = ViewModelProvider(
            this,
            factory
        )[DetailWeatherViewModel::class.java]
    }

    private fun initObservers() {
        viewModel.weather.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                with(binding) {
                    tvForecast.text = it.forecast
                    tvTemp.text = it.temp
                    tvCity.text = it.city
                    tvCountry.text = it.country
                    tvHumidity.text = it.humidity.toString()
                    tvMinTemp.text = it.temp_min.toString()
                    tvMaxTemp.text = it.temp_max.toString()
                    ivWeather.load(it.url)
                    tvWindDirection.text = it.windDirection
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        viewModel.error.observe(viewLifecycleOwner) {
            when (it) {
                is Exception -> {
                    binding.tvWeather.text = getString(R.string.error)
                }
            }
        }
    }
}
