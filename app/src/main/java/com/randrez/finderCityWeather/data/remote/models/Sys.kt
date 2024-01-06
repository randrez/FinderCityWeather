package com.randrez.finderCityWeather.data.remote.models

data class Sys(
    val country: String?,
    val id: Int?,
    val sunrise: Int? = null,
    val sunset: Int? = null,
    val type: Int? = null
)