package com.my_app.data.remote

import com.my_app.data.utils.Constants
import com.my_app.domain.entities.CurrentWeather
import com.my_app.domain.entities.DailyForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServices {

    @GET("forecast")
    suspend fun get7DayForecastByCityFromRemote(
        @Query("q") city_name: String,
        @Query("APPID") appId: String = Constants.DAILY_FORECAST_APP_ID
    ): DailyForecastResponse

    @GET("weather")
    suspend fun getCurrentWeatherByCityFromRemote(
        @Query("q") city_name: String,
        @Query("APPID") appId: String = Constants.DAILY_FORECAST_APP_ID
    ): CurrentWeather
}