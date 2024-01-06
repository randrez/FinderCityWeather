package com.randrez.finderCityWeather.data.remote.api

import com.randrez.finderCityWeather.data.remote.models.WeatherInfoDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface FinderWeatherApi {

    @GET("weather")
    suspend fun getWeatherByCityName(@Query("q") cityName: String): WeatherInfoDTO
}