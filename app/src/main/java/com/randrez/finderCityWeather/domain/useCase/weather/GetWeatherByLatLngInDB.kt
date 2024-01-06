package com.randrez.finderCityWeather.domain.useCase.weather

import com.mapbox.mapboxsdk.geometry.LatLng
import com.randrez.finderCityWeather.domain.mapper.toWeatherInfo
import com.randrez.finderCityWeather.domain.models.WeatherInfo
import com.randrez.finderCityWeather.domain.repository.WeatherRepository
import com.randrez.finderCityWeather.domain.resource.Resource

class GetWeatherByLatLngInDB(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(latLng: LatLng): Resource<WeatherInfo> {
        try {
            val weatherInfoEntity =
                weatherRepository.getWeatherInfoEntityByLatLng(latLng = latLng)
            weatherInfoEntity?.let {
                return Resource.Success(it.toWeatherInfo())
            }
            return Resource.Error("Not found information Weather in DB")
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Not found information Weather ")
        }
    }
}