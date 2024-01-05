package com.randrez.finderCityWeather.presentation.main

sealed class MainEventsUI {
    data class OnEnterCityName(val cityName: String) : MainEventsUI()
    object OnClearTextCityName : MainEventsUI()
    object OnChangeTypeSearch : MainEventsUI()
    data class OnEnterLatitude(val latitude: Double) : MainEventsUI()
    data class OnEnterLongitude(val longitude: Double) : MainEventsUI()
    object OnSearchLatitudeLongitude : MainEventsUI()
}