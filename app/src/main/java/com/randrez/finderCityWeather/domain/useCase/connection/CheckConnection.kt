package com.randrez.finderCityWeather.domain.useCase.connection

import android.content.Context
import com.randrez.finderCityWeather.utils.isInternetAvailable

class CheckConnection(
    private val context: Context
) {
    operator fun invoke(): Boolean =
        isInternetAvailable(context = context)
}