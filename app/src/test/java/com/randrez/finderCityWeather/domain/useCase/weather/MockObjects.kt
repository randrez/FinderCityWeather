package com.randrez.finderCityWeather.domain.useCase.weather

import com.randrez.database.entities.WeatherInfoEntity
import com.randrez.finderCityWeather.data.remote.models.Clouds
import com.randrez.finderCityWeather.data.remote.models.Coordinate
import com.randrez.finderCityWeather.data.remote.models.Main
import com.randrez.finderCityWeather.data.remote.models.Sys
import com.randrez.finderCityWeather.data.remote.models.Weather
import com.randrez.finderCityWeather.data.remote.models.WeatherInfoDTO
import com.randrez.finderCityWeather.data.remote.models.Wind
import com.randrez.finderCityWeather.domain.models.WeatherInfo

fun toMockWeatherInfo(): WeatherInfo =
    WeatherInfo(
        id = 2643743,
        cityName = "London",
        longitude = -0.1257,
        latitude = 51.5085,
        windSpeed = 3.09,
        windDirection = 300,
        pressure = 1012,
        humidity = 89,
        temp = 278.28,
        countryCode = "GB",
        clouds = 100
    )

fun toMockWeatherInfoEntity(): WeatherInfoEntity =
    WeatherInfoEntity(
        id = 2643743,
        cityName = "London",
        longitude = -0.1257,
        latitude = 51.5085,
        windSpeed = 3.09,
        windDirection = 300,
        pressure = 1012,
        humidity = 89,
        temp = 278.28,
        countryCode = "GB",
        clouds = 100
    )

fun toMockWeatherInfoDTO(): WeatherInfoDTO =
    WeatherInfoDTO(
        id = 2643743,
        clouds = Clouds(all = 100),
        base = "stations",
        name = "London",
        code = 200,
        weather = listOf(
            Weather(
                id = 804,
                main = "Clouds",
                description = "overcast clouds",
                icon = "04n"
            )
        ),
        main = Main(
            temp = 278.28,
            humidity = 89,
            pressure = 1012,
        ),
        coordinate = Coordinate(latitude = 51.5085, longitude = -0.1257),
        message = "",
        sys = Sys(id = 2075535, country = "GB"),
        wind = Wind(direction = 300, speed = 3.09)
    )

fun toMockWeatherInfoDTOFail(): WeatherInfoDTO =
    WeatherInfoDTO(
        id = null,
        clouds = Clouds(all = null),
        base = null,
        name = null,
        code = 400,
        weather = null,
        main = null,
        coordinate = null,
        message = "city not found",
        sys = null,
        wind = null
    )