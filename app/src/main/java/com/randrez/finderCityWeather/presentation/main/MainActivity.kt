package com.randrez.finderCityWeather.presentation.main

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.randrez.finderCityWeather.BuildConfig
import com.randrez.finderCityWeather.R
import com.randrez.finderCityWeather.databinding.ActivityMainBinding
import com.randrez.finderCityWeather.domain.models.WeatherInfo
import com.randrez.finderCityWeather.utils.toStringClouds
import com.randrez.finderCityWeather.utils.toStringHumidity
import com.randrez.finderCityWeather.utils.toStringPressure
import com.randrez.finderCityWeather.utils.toStringTemp
import com.randrez.finderCityWeather.utils.toWindDirection
import com.randrez.finderCityWeather.utils.toWindSpeed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mapView: MapView
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        mapView = binding.mapView
        generateMap()
        viewModel.changeSearchLatLng.observe(this) { state ->
            updateFabButton(state)
        }
        viewModel.weatherInfo.observe(this) { weatherInfo ->
            weatherInfo?.let {
                updateMap(weatherInfo = weatherInfo)
            }
        }

        viewModel.stateError.observe(this) {
            if (it.showError) {
                Toast.makeText(this, it.messageError, Toast.LENGTH_SHORT).show()
                viewModel.clearMessageError()
            }
        }
    }

    private fun updateFabButton(state: Boolean) {
        if (state)
            viewModel.updateFabButton(
                color = ContextCompat.getColor(this, R.color.primary),
                icon = R.drawable.baseline_search_24
            )
        else
            viewModel.updateFabButton(
                color = ContextCompat.getColor(this, R.color.primaryDark),
                icon = R.drawable.baseline_location_searching_24
            )
    }

    private fun generateMap() {
        mapView.getMapAsync { mapBoxMap ->
            mapBoxMap.setStyle(BuildConfig.STYLE_MAP)
            mapBoxMap.cameraPosition = CameraPosition.Builder().target(LatLng(0.0, 0.0))
                .zoom(1.0)
                .build()
        }
    }

    private fun updateMap(weatherInfo: WeatherInfo) {
        mapView.getMapAsync { mapBoxMap ->
            mapBoxMap.clear()
            val latLng = LatLng(
                weatherInfo.latitude,
                weatherInfo.longitude
            )
            if (weatherInfo.latitude != 0.0 && weatherInfo.longitude != 0.0) {
                mapBoxMap.addMarker(
                    MarkerOptions().position(latLng)
                )
                mapBoxMap.cameraPosition =
                    CameraPosition.Builder().target(latLng)
                        .zoom(5.0)
                        .build()
                mapBoxMap.setOnMarkerClickListener { clickedMarker ->
                    if (latLng == clickedMarker.position) {
                        dialogWeatherInfo(weatherInfo)
                        true
                    } else
                        false
                }
            } else {
                mapBoxMap.cameraPosition =
                    CameraPosition.Builder().target(latLng)
                        .zoom(1.0)
                        .build()
            }
        }
    }

    private fun dialogWeatherInfo(weatherInfo: WeatherInfo) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_alert_dialog)

        val cityName = dialog.findViewById(R.id.cityName) as TextView
        cityName.text = weatherInfo.cityName.uppercase()

        val country = dialog.findViewById(R.id.country) as TextView
        country.text = weatherInfo.countryCode

        val temp = dialog.findViewById(R.id.temp) as TextView
        temp.text = weatherInfo.temp.toStringTemp()

        val clouds = dialog.findViewById(R.id.clouds) as TextView
        clouds.text = weatherInfo.clouds.toStringClouds()

        val humidity = dialog.findViewById(R.id.humidity) as TextView
        humidity.text = weatherInfo.humidity.toStringHumidity()

        val pressure = dialog.findViewById(R.id.pressure) as TextView
        pressure.text = weatherInfo.pressure.toStringPressure()

        val windDirection = dialog.findViewById(R.id.windDirection) as TextView
        windDirection.text = weatherInfo.windDirection.toWindDirection()

        val windSpeed = dialog.findViewById(R.id.winSpeed) as TextView
        windSpeed.text = weatherInfo.windSpeed.toWindSpeed()

        val btnClose = dialog.findViewById(R.id.closeDilog) as Button
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}