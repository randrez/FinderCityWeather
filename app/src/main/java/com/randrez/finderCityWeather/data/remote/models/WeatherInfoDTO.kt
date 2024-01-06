package com.randrez.finderCityWeather.data.remote.models

import com.google.gson.annotations.SerializedName

data class WeatherInfoDTO(
    val base: String?,
    val clouds: Clouds?,
    @SerializedName("cod")
    val code: Int?,
    @SerializedName("coord")
    val coordinate: Coordinate?,
    val dt: Int? = null,
    val id: Int?,
    val main: Main?,
    val name: String?,
    @SerializedName("sys")
    val sys: Sys?,
    val timezone: Int? = null,
    val visibility: Int? = null,
    val weather: List<Weather?>?,
    val wind: Wind?,
    val message: String? = null
)