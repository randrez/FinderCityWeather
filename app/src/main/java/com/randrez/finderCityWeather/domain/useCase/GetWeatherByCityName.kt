package com.randrez.finderCityWeather.domain.useCase

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.randrez.finderCityWeather.domain.mapper.toWeatherInfo
import com.randrez.finderCityWeather.domain.models.WeatherInfo
import com.randrez.finderCityWeather.domain.repository.WeatherRepository
import com.randrez.finderCityWeather.domain.resource.Resource


class GetWeatherByCityName constructor(
    private val context: Context,
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(cityName: String): Resource<WeatherInfo> {

        val weatherInfoDTO = weatherRepository.getWeatherByCityName(cityName)
        if (weatherInfoDTO.code != 200) {
            val message = weatherInfoDTO.message?.let { it } ?: "Not found information Weather "
            return Resource.Error(message)
        }
        return Resource.Success(weatherInfoDTO.toWeatherInfo())
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(network) ?: return false

            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo?.isConnectedOrConnecting ?: false
        }
    }
}