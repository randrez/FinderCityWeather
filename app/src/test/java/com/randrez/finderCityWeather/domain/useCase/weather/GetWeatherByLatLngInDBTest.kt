package com.randrez.finderCityWeather.domain.useCase.weather

import com.mapbox.mapboxsdk.geometry.LatLng
import com.randrez.finderCityWeather.domain.repository.WeatherRepository
import com.randrez.finderCityWeather.domain.resource.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetWeatherByLatLngInDBTest{

    @MockK
    private lateinit var weatherRepository: WeatherRepository

    private lateinit var getWeatherByLatLngInDB: GetWeatherByLatLngInDB

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getWeatherByLatLngInDB = GetWeatherByLatLngInDB(weatherRepository)
    }

    @Test
    fun `when send latitude, longitude and return information database`() = runBlocking {
        val latLng = LatLng(latitude = 51.5085, longitude = -0.1257)
        coEvery {
            weatherRepository.getWeatherInfoEntityByLatLng(latLng)
        } returns toMockWeatherInfoEntity()
        val result = getWeatherByLatLngInDB(latLng)
        assert(result is Resource.Success)
    }

    @Test
    fun `when send latitude, longitude and no exist information in database`() = runBlocking {
        val latLng = LatLng(latitude = 0.0, longitude = -0.0)
        coEvery {
            weatherRepository.getWeatherInfoEntityByLatLng(latLng)
        } returns null
        val result = getWeatherByLatLngInDB(latLng)
        assert(result is Resource.Error)
    }
}