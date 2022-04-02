package com.example.a2sem.presentation.detailWeather

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.example.a2sem.R
import com.example.a2sem.databinding.FragmentDetailWeatherBinding
import com.example.a2sem.presentation.MainActivity
import com.example.a2sem.utils.AppViewModelFactory
import javax.inject.Inject

private const val ARG_CITY_NAME = "city_name"

class DetailWeatherFragment : Fragment(R.layout.fragment_detail_weather) {

    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentDetailWeatherBinding
    private val viewModel: DetailWeatherViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailWeatherBinding.bind(view)

        initObservers()

        val cityName = arguments?.getString(ARG_CITY_NAME)
        if (cityName != null) {
            viewModel.onGetWeatherId(cityName)
        } else {
            val id = arguments?.getInt("id").toString()
            viewModel.onGetWeather(id)
        }
    }

    private fun initObservers() {
        viewModel.weatherId.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                val id = it.toString()
                viewModel.onGetWeather(id)
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

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
