package com.example.a2sem.presentation.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.a2sem.R
import com.example.a2sem.databinding.FragmentMainBinding
import com.example.a2sem.presentation.detailWeather.DetailWeatherFragment
import com.example.a2sem.presentation.main.adapter.CityListAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_ID = "id"
private const val ARG_CITY_NAME = "city_name"

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private var cityListAdapter: CityListAdapter? = null
    private lateinit var binding: FragmentMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lat = "55.88"
    private var lon = "49.1"
    var id: Int? = null
    private val viewModel: MainViewModel by viewModels()

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                getLastLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                getLastLocation()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        initObservers()

        requestLocationAccess()
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        viewModel.onGetCities(lat, lon)

        with(binding) {
            svCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    getWeatherDetailByName(query)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }
    }

    private fun initObservers() {
        viewModel.cities.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                val cities = it.list as MutableList

                cityListAdapter = CityListAdapter { clickedCity ->
                    getWeatherDetailById(clickedCity)
                    cityListAdapter?.submitList(cities)
                }

                binding.cities.run {
                    adapter = cityListAdapter
                }

                cityListAdapter?.submitList(cities)
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        viewModel.error.observe(viewLifecycleOwner) {
            when (it) {
                is Exception -> {
                    showMessage(R.string.not_find_city)
                }
            }
        }
    }

    private fun getWeatherDetailByName(city: String) {
        val bundle = Bundle()
        bundle.putString(ARG_CITY_NAME, city)
        val detailWeatherFragment = DetailWeatherFragment()
        detailWeatherFragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, detailWeatherFragment)
            ?.addToBackStack("weather")
            ?.commit()
    }

    private fun getWeatherDetailById(it: Int) {
        val bundle = Bundle()
        bundle.putInt(ARG_ID, it)
        val detailWeatherFragment = DetailWeatherFragment()
        detailWeatherFragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, detailWeatherFragment)
            ?.addToBackStack("weather")
            ?.commit()
    }

    private fun requestLocationAccess() {
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun getLastLocation() {
        if (checkPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location == null) {
                        showMessage(R.string.error_getting_location)
                    } else {
                        lat = location.latitude.toString()
                        lon = location.longitude.toString()
                    }
                }
        }
    }

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            binding.root,
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun checkPermission(vararg perm: String): Boolean {
        val havePermissions = perm.toList().all {
            ContextCompat.checkSelfPermission(requireContext(), it) ==
                    PackageManager.PERMISSION_GRANTED
        }
        if (!havePermissions) {
            return false
        }
        return true
    }
}
