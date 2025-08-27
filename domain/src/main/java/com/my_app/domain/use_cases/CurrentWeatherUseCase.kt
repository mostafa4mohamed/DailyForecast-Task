package com.my_app.domain.use_cases

import com.my_app.domain.repo.CurrentWeatherRepo

class CurrentWeatherUseCase(private val repo: CurrentWeatherRepo) {

    // Fetch current weather data for the given city from the remote source
    suspend fun invokeFromRemote(cityName: String) =
        repo.getCurrentWeatherByCityFromRemote(cityName)

    // Fetch current weather data for the given city from the local database
    suspend fun invokeFromLocalData(cityName: String) =
        repo.getCurrentWeatherByCityFromLocalDataBase(cityName)

    // Insert current weather data for the given city into the local database
    suspend fun insertToLocalDataBase(data: com.my_app.domain.entities.CurrentWeather) =
        repo.insertCurrentWeatherByCityToLocalDataBase(data)
}
