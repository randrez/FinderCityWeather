package com.randrez.finderCityWeather.di

import android.content.Context
import com.randrez.finderCityWeather.domain.repository.WeatherRepository
import com.randrez.finderCityWeather.domain.useCase.GetWeatherByCityName
import com.randrez.finderCityWeather.domain.useCase.WeatherUseCase
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
    fun provideWeatherUseCase(
        @ApplicationContext context: Context,
        weatherRepository: WeatherRepository
    ) =
        WeatherUseCase(
            getWeatherByCityName = GetWeatherByCityName(
                context = context,
                weatherRepository = weatherRepository
            )
        )
}