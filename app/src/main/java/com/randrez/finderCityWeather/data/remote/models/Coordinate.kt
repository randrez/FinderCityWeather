package com.randrez.finderCityWeather.data.remote.models

import com.google.gson.annotations.SerializedName

data class Coordinate(
    @SerializedName("lat")
    val latitude: Double?,
    @SerializedName("lon")
    val longitude: Double?
)