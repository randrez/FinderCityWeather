package com.randrez.database.repository

import com.randrez.database.dao.WeatherInfoDAO
import com.randrez.database.entities.WeatherInfoEntity

class WeatherEntitiesRepositoryImpl(
    private val weatherInfoDAO: WeatherInfoDAO
) : WeatherEntitiesRepository {
    override suspend fun insert(weatherInfoEntity: WeatherInfoEntity): Long =
        weatherInfoDAO.insert(weatherInfoEntity = weatherInfoEntity)

    override suspend fun getByCityName(cityName: String): WeatherInfoEntity? =
        weatherInfoDAO.getByCityName(cityName = cityName)

    override suspend fun getByLatLng(latitude: Double, longitude: Double): WeatherInfoEntity? =
        weatherInfoDAO.getByLatLng(latitude = latitude, longitude = longitude)
}