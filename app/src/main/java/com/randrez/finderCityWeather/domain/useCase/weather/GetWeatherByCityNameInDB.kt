package com.randrez.finderCityWeather.domain.useCase.weather

import com.randrez.finderCityWeather.domain.mapper.toWeatherInfo
import com.randrez.finderCityWeather.domain.models.WeatherInfo
import com.randrez.finderCityWeather.domain.repository.WeatherRepository
import com.randrez.finderCityWeather.domain.resource.Resource

class GetWeatherByCityNameInDB(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(cityName: String): Resource<WeatherInfo> {
        try {
            val weatherInfoEntity =
                weatherRepository.getWeatherInfoEntityByCityName(cityName = cityName)
            weatherInfoEntity?.let {
                return Resource.Success(it.toWeatherInfo())
            }
            return Resource.Error("Not found information Weather in DB")
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Not found information Weather ")
        }
    }
}