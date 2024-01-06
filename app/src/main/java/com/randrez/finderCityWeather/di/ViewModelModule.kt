package com.randrez.finderCityWeather.di

import com.randrez.finderCityWeather.domain.repository.WeatherRepository
import com.randrez.finderCityWeather.domain.useCase.weather.GetWeatherByCityName
import com.randrez.finderCityWeather.domain.useCase.weather.GetWeatherByCityNameInDB
import com.randrez.finderCityWeather.domain.useCase.weather.GetWeatherByLatLng
import com.randrez.finderCityWeather.domain.useCase.weather.GetWeatherByLatLngInDB
import com.randrez.finderCityWeather.domain.useCase.weather.SaveWeatherInfoInDB
import com.randrez.finderCityWeather.domain.useCase.weather.WeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideWeatherUseCase(
        weatherRepository: WeatherRepository
    ) = WeatherUseCase(
        getWeatherByCityName = GetWeatherByCityName(
            weatherRepository = weatherRepository
        ),
        getWeatherByLatLng = GetWeatherByLatLng(
            weatherRepository = weatherRepository
        ),
        saveWeatherInfoInDB = SaveWeatherInfoInDB(weatherRepository = weatherRepository),
        getWeatherByCityNameInDB = GetWeatherByCityNameInDB(weatherRepository = weatherRepository),
        getWeatherByLatLngInDB = GetWeatherByLatLngInDB(weatherRepository = weatherRepository)
    )
}