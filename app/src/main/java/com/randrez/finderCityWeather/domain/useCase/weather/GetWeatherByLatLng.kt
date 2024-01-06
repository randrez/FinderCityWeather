package com.randrez.finderCityWeather.domain.useCase.weather

import android.content.Context
import com.mapbox.mapboxsdk.geometry.LatLng
import com.randrez.finderCityWeather.domain.mapper.toWeatherInfo
import com.randrez.finderCityWeather.domain.models.WeatherInfo
import com.randrez.finderCityWeather.domain.repository.WeatherRepository
import com.randrez.finderCityWeather.domain.resource.Resource
import com.randrez.finderCityWeather.utils.Constants

class GetWeatherByLatLng(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(latLng: LatLng?): Resource<WeatherInfo> {
        try {
            val weatherInfoDTO = weatherRepository.getWeatherByLatLng(latLng)
            if (weatherInfoDTO.code != Constants.RESPONSE_SUCCESS) {
                val message = weatherInfoDTO.message?.let { it } ?: "Not found information Weather "
                return Resource.Error(message)
            }
            return Resource.Success(weatherInfoDTO.toWeatherInfo())
        } catch (e: retrofit2.HttpException) {
            return Resource.Error(e.message ?: "Not found information Weather ")
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Not found information Weather ")
        }
    }
}