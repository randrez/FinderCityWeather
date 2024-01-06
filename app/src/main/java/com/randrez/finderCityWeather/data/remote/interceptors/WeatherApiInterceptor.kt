package com.randrez.finderCityWeather.data.remote.interceptors

import com.randrez.finderCityWeather.BuildConfig.API_KEY
import com.randrez.finderCityWeather.data.remote.api.ConstantsApi.APP_ID
import okhttp3.Interceptor
import okhttp3.Response

class WeatherApiInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter(APP_ID, API_KEY)
            .build()

        val newRequest = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }

}