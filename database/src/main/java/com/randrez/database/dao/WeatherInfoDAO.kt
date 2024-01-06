package com.randrez.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.randrez.database.entities.WeatherInfoEntity

@Dao
interface WeatherInfoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherInfoEntity: WeatherInfoEntity): Long

    @Query("SELECT * FROM weather WHERE  cityName COLLATE NOCASE = :cityName COLLATE NOCASE")
    suspend fun getByCityName(cityName: String): WeatherInfoEntity?

    @Query("SELECT * FROM weather WHERE latitude =:latitude AND longitude =:longitude")
    suspend fun getByLatLng(latitude: Double, longitude: Double): WeatherInfoEntity?
}