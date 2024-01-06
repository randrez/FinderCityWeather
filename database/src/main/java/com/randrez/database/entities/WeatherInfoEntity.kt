package com.randrez.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.randrez.database.entities.ConstantsWeatherInfoEntity.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class WeatherInfoEntity(
    @PrimaryKey
    val id:Long,
    val cityName:String,
    val latitude:Double,
    val longitude:Double,
    val countryCode:String,
    val temp:Double,
    val clouds:Long,
    val humidity:Long,
    val pressure:Long,
    val windDirection:Long,
    val windSpeed:Double,
)
