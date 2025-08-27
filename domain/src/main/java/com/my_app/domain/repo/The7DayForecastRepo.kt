package com.my_app.domain.repo

import com.my_app.domain.entities.CurrentWeather
import com.my_app.domain.entities.DailyForecastResponse


interface The7DayForecastRepo {

    suspend fun getDailyForecastByCityFromRemote(
        cityName:String,
    ): DailyForecastResponse?

    suspend fun getDailyForecastByCityFromLocalDataBase(
        cityName:String,
    ): DailyForecastResponse?

    suspend fun insertDailyForecastByCityToLocalDataBase(
        data: List<CurrentWeather>
    )

}