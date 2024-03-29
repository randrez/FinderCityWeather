package com.randrez.finderCityWeather.domain.mapper

import com.randrez.database.entities.WeatherInfoEntity
import com.randrez.finderCityWeather.data.remote.models.WeatherInfoDTO
import com.randrez.finderCityWeather.domain.models.WeatherInfo
import com.randrez.finderCityWeather.utils.kelvinToCelsius

fun WeatherInfoDTO.toWeatherInfo() =
    WeatherInfo(
        latitude = this.coordinate?.latitude ?: 0.0,
        longitude = this.coordinate?.longitude ?: 0.0,
        countryCode = this.sys?.country ?: "",
        temp = kelvinToCelsius(this.main?.temp ?: 0.0),
        clouds = this.clouds?.all?.toLong() ?: 0,
        humidity = this.main?.humidity?.toLong() ?: 0,
        pressure = this.main?.pressure?.toLong() ?: 0,
        windDirection = this.wind?.direction?.toLong() ?: 0,
        windSpeed = this.wind?.speed ?: 0.0,
        cityName = this.name ?: "",
        id = this.id?.toLong() ?: 0
    )

fun WeatherInfo.toWeatherInfoEntity(): WeatherInfoEntity =
    WeatherInfoEntity(
        id = id,
        cityName = cityName,
        latitude = latitude,
        longitude = longitude,
        countryCode = countryCode,
        temp = temp,
        clouds = clouds,
        humidity = humidity,
        pressure = pressure,
        windDirection = windDirection,
        windSpeed = windSpeed
    )

fun WeatherInfoEntity.toWeatherInfo(): WeatherInfo =
    WeatherInfo(
        latitude = latitude,
        longitude = longitude,
        countryCode = countryCode,
        temp = temp,
        clouds = clouds,
        humidity = humidity,
        pressure = pressure,
        windDirection = windDirection,
        windSpeed = windSpeed,
        cityName = cityName,
        id = id
    )