package com.randrez.finderCityWeather.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.randrez.finderCityWeather.domain.resource.Resource
import com.randrez.finderCityWeather.domain.useCase.connection.CheckConnection
import com.randrez.finderCityWeather.domain.useCase.weather.WeatherUseCase
import com.randrez.finderCityWeather.domain.useCase.weather.toMockWeatherInfo
import com.randrez.finderCityWeather.presentation.main.MainEventsUI.OnChangeCityName
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


class MainViewModelTest {

    @MockK
    private lateinit var weatherUseCase: WeatherUseCase

    @MockK
    private lateinit var checkConnection: CheckConnection

    private lateinit var viewModel: MainViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(weatherUseCase, checkConnection)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }


    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when search city name and set state city name`() = runTest {
        val cityName = "London"
        val latch = CountDownLatch(1)

        viewModel.handleUIEvent(OnChangeCityName(cityName))

        advanceUntilIdle()
        viewModel.cityName.observeForever {
            if (it != null) {
                assertEquals(cityName, it)
                latch.countDown()
            }
        }

        assertTrue(latch.await(2, TimeUnit.SECONDS))
    }

    @Test
    fun `when search city name in viewmodel and api return Weather object`() = runTest {
        val cityName = "London"
        val isConnected = true
        val latch = CountDownLatch(1)

        coEvery { checkConnection() } returns isConnected
        coEvery {
            weatherUseCase.getWeatherByCityName(cityName)
        } returns Resource.Success(
            toMockWeatherInfo()
        )
        coEvery {
            weatherUseCase.saveWeatherInfoInDB(toMockWeatherInfo())
        } returns Resource.Success(toMockWeatherInfo())

        viewModel.handleUIEvent(OnChangeCityName(cityName))
        viewModel.getWeatherInfoByCityName()

        advanceUntilIdle()

        val expectedWeatherInfo = toMockWeatherInfo()
        viewModel.weatherInfo.observeForever {
            if (it != null) {
                assertEquals(expectedWeatherInfo, it)
                latch.countDown()
            }
        }

        assertTrue(latch.await(2, TimeUnit.SECONDS))
    }

    @Test
    fun `when search city name in viewmodel and api return error`() = runTest {
        val cityNameFail = "XXXX"
        val isConnected = true
        val latch = CountDownLatch(1)
        coEvery { checkConnection() } returns isConnected

        coEvery {
            weatherUseCase.getWeatherByCityName(cityNameFail)
        } returns Resource.Error("Error return api")

        viewModel.handleUIEvent(OnChangeCityName(cityNameFail))
        viewModel.getWeatherInfoByCityName()

        advanceUntilIdle()
        viewModel.stateError.observeForever {
            assertEquals(true, it.showError)
            latch.countDown()
        }

        assertTrue(latch.await(2, TimeUnit.SECONDS))
    }

    @Test
    fun `when save weather local is success`() = runTest {
        val expectedWeatherInfo = toMockWeatherInfo()
        val latch = CountDownLatch(1)

        coEvery {
            weatherUseCase.saveWeatherInfoInDB(expectedWeatherInfo)
        } returns Resource.Success(expectedWeatherInfo)

        viewModel.saveWeatherInfo(expectedWeatherInfo)

        advanceUntilIdle()
        viewModel.weatherInfo.observeForever {
            if (it != null) {
                assertEquals(expectedWeatherInfo, it)
                latch.countDown()
            }
        }

        assertTrue(latch.await(2, TimeUnit.SECONDS))
    }

    @Test
    fun `when save weather local is fail`() = runTest {

        val latch = CountDownLatch(1)

        coEvery {
            weatherUseCase.saveWeatherInfoInDB(null)
        } returns Resource.Error("Error save local db")

        viewModel.saveWeatherInfo(null)

        advanceUntilIdle()

        viewModel.stateError.observeForever {
            assertEquals(true, it.showError)
            latch.countDown()
        }

        assertTrue(latch.await(2, TimeUnit.SECONDS))
    }

    @Test
    fun `when search city name in db local and return Weather object`() = runTest {
        val cityName = "London"
        val isConnected = false
        val latch = CountDownLatch(1)

        coEvery { checkConnection() } returns isConnected
        coEvery {
            weatherUseCase.getWeatherByCityNameInDB(cityName)
        } returns Resource.Success(
            toMockWeatherInfo()
        )

        viewModel.handleUIEvent(OnChangeCityName(cityName))

        viewModel.getWeatherInByCityNameInDB(cityName)

        advanceUntilIdle()

        val expectedWeatherInfo = toMockWeatherInfo()
        viewModel.weatherInfo.observeForever {
            if (it != null) {
                assertEquals(expectedWeatherInfo, it)
                latch.countDown()
            }
        }

        assertTrue(latch.await(2, TimeUnit.SECONDS))
    }

    @Test
    fun `when search city name in db local but is fail`() = runTest {
        val cityName = "XXXX"
        val isConnected = false
        val latch = CountDownLatch(1)
        coEvery { checkConnection() } returns isConnected
        coEvery {
            weatherUseCase.getWeatherByCityNameInDB(cityName)
        } returns Resource.Error("Not found weather information in db")

        viewModel.handleUIEvent(OnChangeCityName(cityName))
        viewModel.getWeatherInByCityNameInDB(cityName)

        advanceUntilIdle()

        viewModel.stateError.observeForever {
            assertEquals(true, it.showError)
            latch.countDown()
        }

        assertTrue(latch.await(2, TimeUnit.SECONDS))
    }
}