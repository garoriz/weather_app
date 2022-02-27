package com.example.a2sem

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.a2sem.adapter.CityListAdapter
import com.example.a2sem.data.WeatherRepository
import com.example.a2sem.data.api.response.cities.City
import com.example.a2sem.databinding.FragmentMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.lang.Exception

class MainFragment : Fragment(R.layout.fragment_main) {

    private var cityListAdapter: CityListAdapter? = null
    private lateinit var binding: FragmentMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lat = "55.88"
    private var lon = "49.1"

    private val repository by lazy {
        WeatherRepository()
    }

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

        lifecycleScope.launch {
            requestLocationAccess()

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

            val cities = getCities(lat, lon)

            cityListAdapter = CityListAdapter {
                getWeatherDetailById(it)
                cityListAdapter?.submitList(cities)
            }

            binding.cities.run {
                adapter = cityListAdapter
            }

            cityListAdapter?.submitList(cities)
        }

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

    private fun getWeatherDetailById(it: Int) {
        lifecycleScope.launch {
            try {
                val bundle = Bundle()
                bundle.putInt("id", it)
                val detailWeatherFragment = DetailWeatherFragment()
                detailWeatherFragment.arguments = bundle
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, detailWeatherFragment)
                    ?.addToBackStack("weather")
                    ?.commit()
            } catch (ex: Exception) {
                showMessage(R.string.not_find_city)
            }
        }
    }

    private fun getWeatherDetailByName(city: String) {
        lifecycleScope.launch {
            try {
                val bundle = Bundle()
                val id = repository.getWeatherByName(city).id
                bundle.putInt("id", id)
                val detailWeatherFragment = DetailWeatherFragment()
                detailWeatherFragment.arguments = bundle
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, detailWeatherFragment)
                    ?.addToBackStack("weather")
                    ?.commit()
            } catch (ex: Exception) {
                showMessage(R.string.not_find_city)
            }
        }
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

    private suspend fun getCities(lat: String, lon: String): MutableList<City> {
        return repository.getCities(lat, lon).list as MutableList<City>
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
