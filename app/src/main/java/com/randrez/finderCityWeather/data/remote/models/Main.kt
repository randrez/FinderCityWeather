package com.randrez.finderCityWeather.data.remote.models

data class Main(
    val feels_like: Double? = null,
    val humidity: Int?,
    val pressure: Int?,
    val temp: Double?,
    val temp_max: Double? = null,
    val temp_min: Double? = null
)