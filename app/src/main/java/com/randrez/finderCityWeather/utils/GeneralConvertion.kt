package com.randrez.finderCityWeather.utils

import com.randrez.finderCityWeather.utils.Constants.CONSTANT_CONVERSION_KELVIN

fun kelvinToCelsius(tempKelvin: Double): Double =
    tempKelvin - CONSTANT_CONVERSION_KELVIN

fun Double.toStringTemp() =
    "${this}°C"

fun Long.toStringClouds() =
    "${this}%"

fun Long.toStringHumidity() =
    "${this}%"

fun Long.toStringPressure() =
    "${this}hPa"

fun Long.toWindDirection() =
    "${this}°"

fun Double.toWindSpeed() =
    "${this}m/s"