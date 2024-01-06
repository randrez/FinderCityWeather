package com.randrez.finderCityWeather.domain.useCase.weather

import com.randrez.finderCityWeather.domain.repository.WeatherRepository
import com.randrez.finderCityWeather.domain.resource.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetWeatherByCityNameInDBTest {

    @MockK
    private lateinit var weatherRepository: WeatherRepository

    private lateinit var getWeatherByCityNameInDB: GetWeatherByCityNameInDB

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getWeatherByCityNameInDB = GetWeatherByCityNameInDB(weatherRepository)
    }

    @Test
    fun `when send city name to database and exist information`() = runBlocking {
        val cityName = "London"
        coEvery {
            weatherRepository.getWeatherInfoEntityByCityName(cityName)
        } returns toMockWeatherInfoEntity()
        val result = getWeatherByCityNameInDB(cityName)
        assert(result is Resource.Success)
    }

    @Test
    fun `when send city name to database and no exist information`() = runBlocking {
        val cityName = "Paris"
        coEvery {
            weatherRepository.getWeatherInfoEntityByCityName(cityName)
        } returns null
        val result = getWeatherByCityNameInDB(cityName)
        assert(result is Resource.Error)
    }
}