package com.randrez.finderCityWeather.domain.useCase.weather

import com.randrez.finderCityWeather.data.remote.models.WeatherInfoDTO
import com.randrez.finderCityWeather.domain.repository.WeatherRepository
import com.randrez.finderCityWeather.domain.resource.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class GetWeatherByCityNameTest {

    @MockK
    private lateinit var weatherRepository: WeatherRepository

    private lateinit var getWeatherByCityName: GetWeatherByCityName

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getWeatherByCityName = GetWeatherByCityName(weatherRepository)
    }

    @Test
    fun `when send city name and api response success`() = runBlocking {
        val cityName = "London"
        coEvery {
            weatherRepository.getWeatherByCityName(cityName)
        } returns toMockWeatherInfoDTO()
        val result = getWeatherByCityName(cityName)
        assert(result is Resource.Success)
    }

    @Test
    fun `when send city name and api response fail`() = runBlocking {
        val cityName = "XXX"
        coEvery {
            weatherRepository.getWeatherByCityName(cityName)
        } returns toMockWeatherInfoDTOFail()
        val result = getWeatherByCityName(cityName)
        assert(result is Resource.Error)
    }

    @Test
    fun `when send city name and return http exception`() = runBlocking {
        val cityName = "InvalidCity"
        coEvery {
            weatherRepository.getWeatherByCityName(cityName)
        } throws retrofit2.HttpException(
            Response.error<WeatherInfoDTO>(
                400,
                "Bad Request".toResponseBody()
            )
        )

        val result = getWeatherByCityName(cityName)
        assert(result is Resource.Error)
    }
}