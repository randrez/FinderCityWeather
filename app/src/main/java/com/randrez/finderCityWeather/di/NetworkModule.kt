package com.randrez.finderCityWeather.di

import com.randrez.finderCityWeather.BuildConfig
import com.randrez.finderCityWeather.data.remote.api.FinderWeatherApi
import com.randrez.finderCityWeather.data.remote.interceptors.WeatherApiInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoginInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

    @Provides
    @Singleton
    fun provideWeatherApiInterceptor(): WeatherApiInterceptor {
        return WeatherApiInterceptor()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        weatherApiInterceptor: WeatherApiInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(weatherApiInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideGson(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(gson: GsonConverterFactory, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(gson)
            .client(client)
            .baseUrl(BuildConfig.URL_PUBLIC).build()

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): FinderWeatherApi =
        retrofit.create(FinderWeatherApi::class.java)
}