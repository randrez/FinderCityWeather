package com.randrez.finderCityWeather.domain.useCase.weather

data class WeatherUseCase(
    val getWeatherByCityName: GetWeatherByCityName,
    val getWeatherByLatLng: GetWeatherByLatLng,
    val saveWeatherInfoInDB: SaveWeatherInfoInDB,
    val getWeatherByCityNameInDB: GetWeatherByCityNameInDB,
    val getWeatherByLatLngInDB: GetWeatherByLatLngInDB
)
