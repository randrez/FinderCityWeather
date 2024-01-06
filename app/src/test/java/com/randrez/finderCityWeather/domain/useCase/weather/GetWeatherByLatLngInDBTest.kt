package com.randrez.finderCityWeather.domain.useCase.weather

import com.randrez.finderCityWeather.domain.repository.WeatherRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before


class GetWeatherByLatLngInDBTest{

    @MockK
    private lateinit var weatherRepository: WeatherRepository

    private lateinit var getWeatherByLatLngInDB: GetWeatherByLatLngInDB

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getWeatherByLatLngInDB = GetWeatherByLatLngInDB(weatherRepository)
    }


}