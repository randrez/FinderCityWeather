package com.randrez.finderCityWeather.domain.useCase.weather

import com.randrez.finderCityWeather.domain.mapper.toWeatherInfoEntity
import com.randrez.finderCityWeather.domain.models.WeatherInfo
import com.randrez.finderCityWeather.domain.repository.WeatherRepository
import com.randrez.finderCityWeather.domain.resource.Resource

class SaveWeatherInfoInDB(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(weatherInfo: WeatherInfo?): Resource<WeatherInfo> {
        try {
            weatherInfo?.let {
                val saveSuccess = weatherRepository.setWeatherInfoLocal(it.toWeatherInfoEntity())
                if (saveSuccess > 0) return Resource.Success(weatherInfo)
                else return Resource.Error("Exception save information in DB")
            } ?: return Resource.Error("Exception save information in DB")
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(e.message ?: "Not found information Weather ")
        }
    }
}