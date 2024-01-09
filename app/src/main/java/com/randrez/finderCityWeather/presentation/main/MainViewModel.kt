package com.randrez.finderCityWeather.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.mapbox.mapboxsdk.geometry.LatLng
import com.randrez.finderCityWeather.domain.models.WeatherInfo
import com.randrez.finderCityWeather.domain.resource.Resource
import com.randrez.finderCityWeather.domain.useCase.connection.CheckConnection
import com.randrez.finderCityWeather.domain.useCase.weather.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
    private val checkConnection: CheckConnection
) : ViewModel() {

    private val _fbButtonState: MutableLiveData<FbButtonState> =
        MutableLiveData<FbButtonState>(FbButtonState())
    val fbButtonState: LiveData<FbButtonState> = _fbButtonState.distinctUntilChanged()

    private val _weatherInfo = MutableLiveData<WeatherInfo?>(null)
    val weatherInfo: LiveData<WeatherInfo?> = _weatherInfo.distinctUntilChanged()

    private val _changeSearchLatLng = MutableLiveData(false)
    val changeSearchLatLng: LiveData<Boolean> = _changeSearchLatLng.distinctUntilChanged()

    private val _cityName = MutableLiveData("")
    val cityName: LiveData<String> = _cityName.distinctUntilChanged()

    private val _latitude = MutableLiveData("")
    val latitude: LiveData<String> = _latitude.distinctUntilChanged()

    private val _longitude = MutableLiveData("")
    val longitude: LiveData<String> = _longitude.distinctUntilChanged()

    private val _progress = MutableLiveData(false)
    val progress: LiveData<Boolean> = _progress.distinctUntilChanged()

    private val _stateError = MutableLiveData(StateError())
    val stateError: LiveData<StateError> = _stateError.distinctUntilChanged()

    fun updateFabButton(color: Int, icon: Int) {
        _fbButtonState.value = fbButtonState.value?.copy(colorButton = color, iconButton = icon)
    }

    fun handleUIEvent(eventsUI: MainEventsUI) {
        when (eventsUI) {
            is MainEventsUI.OnChangeTypeSearch -> {
                _changeSearchLatLng.value = changeSearchLatLng.value?.let {
                    !it
                } ?: false
            }

            is MainEventsUI.OnChangeCityName -> {
                viewModelScope.launch {
                    _cityName.postValue(eventsUI.cityName)
                }
            }

            is MainEventsUI.OnClearTextCityName -> {
                viewModelScope.launch {
                    _cityName.postValue("")
                }
            }

            is MainEventsUI.OnSearchByCityName -> getWeatherInfoByCityName()

            is MainEventsUI.OnChangeTextLatitude -> {
                viewModelScope.launch {
                    _latitude.postValue(eventsUI.latitude)
                }
            }

            is MainEventsUI.OnChangeTextLongitude -> {
                viewModelScope.launch {
                    _latitude.postValue(eventsUI.longitude)
                }
            }

            is MainEventsUI.OnSearchLatitudeLongitude -> getWeatherInfoByLatLng()
        }
    }

    fun getWeatherInfoByCityName() {
        _cityName.value?.let { cityName ->
            if (cityName.isNotBlank()) {
                _progress.value = true
                if (checkConnection.invoke()) {
                    viewModelScope.launch {
                        when (val result = weatherUseCase.getWeatherByCityName.invoke(cityName)) {
                            is Resource.Error -> {
                                _progress.value = false
                                _cityName.postValue("")
                                _stateError.value = stateError.value?.copy(
                                    showError = true,
                                    messageError = result.message ?: "Occurred exception"
                                )
                            }

                            is Resource.Success -> {
                                result.data?.let { weatherInfo ->
                                    saveWeatherInfo(weatherInfo)
                                }
                            }
                        }
                    }
                } else {
                    getWeatherInByCityNameInDB(cityName = cityName)
                }
            }
        }
    }

    suspend fun saveWeatherInfo(weatherInfo: WeatherInfo?) {
        weatherInfo?.cityName = _cityName.value ?: weatherInfo?.cityName ?: ""
        when (val result =
            weatherUseCase.saveWeatherInfoInDB.invoke(weatherInfo)) {
            is Resource.Error -> {
                _progress.value = false
                _stateError.value = stateError.value?.copy(
                    showError = true,
                    messageError = result.message
                        ?: "Occurred exception"
                )
            }

            is Resource.Success -> {
                _progress.value = false
                result.data?.let { weatherInfo ->
                    _weatherInfo.postValue(weatherInfo)
                }
            }
        }
    }

    fun getWeatherInByCityNameInDB(cityName: String) {
        viewModelScope.launch {
            when (val result = weatherUseCase.getWeatherByCityNameInDB.invoke(cityName)) {
                is Resource.Error -> {
                    _progress.value = false
                    _stateError.value = stateError.value?.copy(
                        showError = true,
                        messageError = result.message ?: "Occurred exception"
                    )
                }

                is Resource.Success -> {
                    _progress.value = false
                    result.data?.let { weatherInfo ->
                        _weatherInfo.postValue(weatherInfo)
                    }
                }
            }
        }
    }

    fun getWeatherInfoByLatLng() {
        _latitude.value?.let { latitude ->
            _longitude.value?.let { longitude ->
                if (latitude.isNotBlank() && longitude.isNotBlank()) {
                    if (checkConnection.invoke()) {
                        _progress.value = true
                        viewModelScope.launch {
                            when (val result = weatherUseCase.getWeatherByLatLng.invoke(
                                LatLng(
                                    latitude = latitude.toDouble(),
                                    longitude = longitude.toDouble()
                                )
                            )) {
                                is Resource.Error -> {
                                    _progress.value = false
                                    _cityName.postValue("")
                                    _stateError.value = stateError.value?.copy(
                                        showError = true,
                                        messageError = result.message ?: "Occurred exception"
                                    )
                                }

                                is Resource.Success -> {
                                    result.data?.let { weatherInfo ->
                                        saveWeatherInfo(weatherInfo)
                                    }
                                }
                            }
                        }
                    } else {
                        getWeatherInByLatLngInDB(
                            latLng = LatLng(
                                latitude = latitude.toDouble(),
                                longitude = longitude.toDouble()
                            )
                        )
                    }
                }
            }
        }
    }

    fun getWeatherInByLatLngInDB(latLng: LatLng) {
        viewModelScope.launch {
            when (val result = weatherUseCase.getWeatherByLatLngInDB.invoke(latLng)) {
                is Resource.Error -> {
                    _progress.value = false
                    _stateError.value = stateError.value?.copy(
                        showError = true,
                        messageError = result.message ?: "Occurred exception"
                    )
                }

                is Resource.Success -> {
                    _progress.value = false
                    result.data?.let { weatherInfo ->
                        _weatherInfo.postValue(weatherInfo)
                    }
                }
            }
        }
    }

    fun clearMessageError() {
        _stateError.value = stateError.value?.copy(showError = false, messageError = "")
    }
}

