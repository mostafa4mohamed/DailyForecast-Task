package com.my_app.domain.use_cases

import com.my_app.domain.repo.The7DayForecastRepo

class Get7DayForecastByCityUseCase (private val repo: The7DayForecastRepo) {

    /**
     * Fetches the 7-day weather forecast from the remote server for a given city.
     *
     * @param cityName The name of the city.
     * @return The 7-day weather forecast data fetched from the remote server.
     */
    suspend fun invokeFromRemote(
        cityName:String
    ) = repo.getDailyForecastByCityFromRemote(cityName)

    /**
     * Fetches the 7-day weather forecast from the local database for a given city.
     *
     * @param cityName The name of the city.
     * @return The 7-day weather forecast data fetched from the local database.
     */
    suspend fun invokeFromLocalData(
        cityName:String
    ) = repo.getDailyForecastByCityFromLocalDataBase(cityName)

}