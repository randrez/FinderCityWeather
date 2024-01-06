package com.randrez.finderCityWeather.di

import com.randrez.finderCityWeather.data.repository.WeatherRepositoryImpl
import com.randrez.finderCityWeather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun binWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository
}