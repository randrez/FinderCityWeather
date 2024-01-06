package com.randrez.finderCityWeather.data.remote.models

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg")
    val direction: Int?,
    val speed: Double?
)