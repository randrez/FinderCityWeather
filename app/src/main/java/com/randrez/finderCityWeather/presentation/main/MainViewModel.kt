package com.randrez.finderCityWeather.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import com.mapbox.mapboxsdk.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {

    private val _fbButtonState: MutableLiveData<FbButtonState> =
        MutableLiveData<FbButtonState>(FbButtonState())
    val fbButtonState: LiveData<FbButtonState> = _fbButtonState.distinctUntilChanged()

    private val _latitudeLongitude = MutableLiveData(LatLng(0.0, 0.0))
    val latitudeLongitude: LiveData<LatLng> = _latitudeLongitude.distinctUntilChanged()

    private val _changeSearchLatLng = MutableLiveData(false)
    val changeSearchLatLng: LiveData<Boolean> = _changeSearchLatLng.distinctUntilChanged()

    private val _cityName = MutableLiveData("")
    val cityName: LiveData<String> = _cityName.distinctUntilChanged()

    private val _latitude = MutableLiveData("")
    val latitude: LiveData<String> = _latitude.distinctUntilChanged()

    private val _longitude = MutableLiveData("")
    val longitude: LiveData<String> = _longitude.distinctUntilChanged()

    fun updateFabButton(color: Int, icon: Int) {
        _fbButtonState.value = fbButtonState.value?.copy(colorButton = color, iconButton = icon)
    }

    fun handleEvent(eventsUI: MainEventsUI) {
        when (eventsUI) {
            is MainEventsUI.OnChangeTypeSearch -> {
                _changeSearchLatLng.value = changeSearchLatLng.value?.let {
                    !it
                } ?: false
            }

            is MainEventsUI.OnEnterCityName -> {
                _cityName.value = eventsUI.cityName
                _latitudeLongitude.value = LatLng(4.60971, -74.08175)
            }

            is MainEventsUI.OnClearTextCityName -> {
                _cityName.value = ""
                _latitudeLongitude.value = LatLng(0.0, 0.0)
            }

            else -> {}
        }
    }
}