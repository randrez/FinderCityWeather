package com.randrez.finderCityWeather.presentation.main

sealed class MainEventsUI {
    data class OnChangeCityName(val cityName: String) : MainEventsUI()
    object OnClearTextCityName : MainEventsUI()
    object OnSearchByCityName : MainEventsUI()
    object OnChangeTypeSearch : MainEventsUI()
    data class OnChangeTextLatitude(val latitude: String) : MainEventsUI()
    data class OnChangeTextLongitude(val longitude: String) : MainEventsUI()
    object OnSearchLatitudeLongitude : MainEventsUI()
}