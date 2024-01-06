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


class GetWeatherByLatLngTest {

    @MockK
    private lateinit var weatherRepository: WeatherRepository

    private lateinit var getWeatherByLatLng: GetWeatherByLatLng

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getWeatherByLatLng = GetWeatherByLatLng(weatherRepository)
    }

    @Test
    fun `when send latitude, longitude and api response success`() = runBlocking {
        val latLng = LatLng(latitude = 51.5085, longitude = -0.1257)
        coEvery {
            weatherRepository.getWeatherByLatLng(latLng)
        } returns toMockWeatherInfoDTO()
        val result = getWeatherByLatLng(latLng)
        assert(result is Resource.Success)
    }

    @Test
    fun `when send latitude, longitude and api response fail`() = runBlocking {
        val latLng = null
        coEvery {
            weatherRepository.getWeatherByLatLng(latLng)
        } returns toMockWeatherInfoDTOFail()
        val result = getWeatherByLatLng(latLng)
        assert(result is Resource.Error)
    }
}