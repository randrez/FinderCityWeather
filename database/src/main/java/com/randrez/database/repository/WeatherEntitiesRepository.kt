package com.randrez.database.repository

import com.randrez.database.entities.WeatherInfoEntity

interface WeatherEntitiesRepository {
    suspend fun insert(weatherInfoEntity: WeatherInfoEntity): Long
    suspend fun getByCityName(cityName: String): WeatherInfoEntity?
    suspend fun getByLatLng(latitude: Double, longitude: Double): WeatherInfoEntity?
}