package com.randrez.finderCityWeather.presentation.main

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
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
                        Toast.makeText(this, weatherInfo.cityName, Toast.LENGTH_SHORT).show()
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

    private fun dialogWeatherInfo(weatherInfo: WeatherInfo){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_alert_dialog)

        val body = dialog.findViewById(R.id.body) as TextView
        body.text = title

        val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
        yesBtn.setOnClickListener {
            dialog.dismiss()
        }

        val noBtn = dialog.findViewById(R.id.noBtn) as Button
        noBtn.setOnClickListener {
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