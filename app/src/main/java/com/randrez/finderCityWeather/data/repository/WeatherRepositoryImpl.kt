package com.randrez.finderCityWeather.data.repository

import com.mapbox.mapboxsdk.geometry.LatLng
import com.randrez.finderCityWeather.data.remote.api.FinderWeatherApi
import com.randrez.finderCityWeather.data.remote.models.WeatherInfoDTO
import com.randrez.finderCityWeather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: FinderWeatherApi
):WeatherRepository {
    override suspend fun getWeatherByCityName(cityName: String): WeatherInfoDTO =
        weatherApi.getWeatherByCityName(cityName)


    override suspend fun getWeatherByLatLng(latLng: LatLng): WeatherInfoDTO {
        TODO("Not yet implemented")
    }
}