package com.randrez.finderCityWeather.domain.useCase.weather

import com.randrez.finderCityWeather.domain.repository.WeatherRepository
import com.randrez.finderCityWeather.domain.resource.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class SaveWeatherInfoInDBTest {
    @MockK
    private lateinit var weatherRepository: WeatherRepository

    private lateinit var saveWeatherInfoInDB: SaveWeatherInfoInDB

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        saveWeatherInfoInDB = SaveWeatherInfoInDB(weatherRepository)
    }

    @Test
    fun `when save success weather information`() = runBlocking {
        val weatherInfoEntity = toMockWeatherInfoEntity()
        coEvery {
            weatherRepository.setWeatherInfoLocal(weatherInfoEntity)
        } returns 1
        val result = saveWeatherInfoInDB(toMockWeatherInfo())
        assert(result is Resource.Success)
    }

    @Test
    fun `when save fail weather information`() = runBlocking {
        val weatherInfoEntity = toMockWeatherInfoEntity()
        coEvery {
            weatherRepository.setWeatherInfoLocal(weatherInfoEntity)
        } returns 0
        val result = saveWeatherInfoInDB(toMockWeatherInfo())
        assert(result is Resource.Error)
    }
}