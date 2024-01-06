package com.randrez.finderCityWeather.domain.models

data class WeatherInfo (
    val latitude:Double,
    val longitude:Double,
    val countryCode:String,
    val temp:Double,
    val clouds:Long,
    val humidity:Long,
    val pressure:Long,
    val windDirection:Long,
    val windSpeed:Double,
    val cityName:String,
    val id:Long
)