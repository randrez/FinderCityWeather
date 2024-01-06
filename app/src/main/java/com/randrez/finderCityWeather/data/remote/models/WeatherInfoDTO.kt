package com.randrez.finderCityWeather.data.remote.models

import com.google.gson.annotations.SerializedName

data class WeatherInfoDTO(
    val base: String?,
    val clouds: Clouds?,
    @SerializedName("cod")
    val code: Int?,
    @SerializedName("coord")
    val coordinate: Coordinate?,
    val dt: Int?,
    val id: Int?,
    val main: Main?,
    val name: String?,
    @SerializedName("sys")
    val sys: Sys?,
    val timezone: Int?,
    val visibility: Int?,
    val weather: List<Weather?>?,
    val wind: Wind?,
    val message:String?
)