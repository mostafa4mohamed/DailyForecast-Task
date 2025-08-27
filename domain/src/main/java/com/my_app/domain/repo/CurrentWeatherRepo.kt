package com.my_app.domain.repo

import com.my_app.domain.entities.CurrentWeather

interface CurrentWeatherRepo {

    suspend fun getCurrentWeatherByCityFromRemote(
        cityName: String,
    ): CurrentWeather?

    suspend fun getCurrentWeatherByCityFromLocalDataBase(
        cityName: String,
    ): CurrentWeather?

    suspend fun insertCurrentWeatherByCityToLocalDataBase(
        data: CurrentWeather
    )
}