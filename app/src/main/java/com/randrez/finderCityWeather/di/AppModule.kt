package com.randrez.finderCityWeather.di

import android.app.Application
import android.content.Context
import com.randrez.database.AppDataBase
import com.randrez.database.repository.WeatherEntitiesRepository
import com.randrez.database.repository.WeatherEntitiesRepositoryImpl
import com.randrez.finderCityWeather.domain.useCase.connection.CheckConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDataBase {
        return AppDataBase.getInstance(application.applicationContext)
    }

    @Provides
    @Singleton
    fun provideCheckConnection(@ApplicationContext context: Context): CheckConnection =
        CheckConnection(context)

    @Provides
    @Singleton
    fun provideWeatherEntitiesRepository(appDataBase: AppDataBase): WeatherEntitiesRepository =
        WeatherEntitiesRepositoryImpl(weatherInfoDAO = appDataBase.weatherInfoDao())
}