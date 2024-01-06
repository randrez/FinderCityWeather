package com.randrez.finderCityWeather.domain.repository

import com.mapbox.mapboxsdk.geometry.LatLng
import com.randrez.finderCityWeather.data.remote.models.WeatherInfoDTO

interface WeatherRepository {
    suspend fun getWeatherByCityName(cityName: String): WeatherInfoDTO

    suspend fun getWeatherByLatLng(latLng: LatLng): WeatherInfoDTO
}