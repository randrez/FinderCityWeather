package com.randrez.finderCityWeather.data.repository

import com.mapbox.mapboxsdk.geometry.LatLng
import com.randrez.database.entities.WeatherInfoEntity
import com.randrez.database.repository.WeatherEntitiesRepository
import com.randrez.finderCityWeather.data.remote.api.FinderWeatherApi
import com.randrez.finderCityWeather.data.remote.models.WeatherInfoDTO
import com.randrez.finderCityWeather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: FinderWeatherApi,
    private val weatherEntitiesRepository: WeatherEntitiesRepository
) : WeatherRepository {
    override suspend fun getWeatherByCityName(cityName: String): WeatherInfoDTO =
        weatherApi.getWeatherByCityName(cityName)


    override suspend fun getWeatherByLatLng(latLng: LatLng?): WeatherInfoDTO =
        weatherApi.getWeatherByLatLng(latitude = latLng?.latitude, longitude = latLng?.longitude)

    override suspend fun setWeatherInfoLocal(weatherInfoEntity: WeatherInfoEntity): Long =
        weatherEntitiesRepository.insert(weatherInfoEntity = weatherInfoEntity)

    override suspend fun getWeatherInfoEntityByCityName(cityName: String): WeatherInfoEntity? =
        weatherEntitiesRepository.getByCityName(cityName = cityName)

    override suspend fun getWeatherInfoEntityByLatLng(latLng: LatLng): WeatherInfoEntity? =
        weatherEntitiesRepository.getByLatLng(
            latitude = latLng.latitude,
            longitude = latLng.longitude
        )
}