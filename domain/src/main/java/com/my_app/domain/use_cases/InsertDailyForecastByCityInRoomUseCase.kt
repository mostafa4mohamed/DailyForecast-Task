package com.my_app.domain.use_cases

import com.my_app.domain.entities.CurrentWeather
import com.my_app.domain.repo.The7DayForecastRepo

class InsertDailyForecastByCityInRoomUseCase(private val repo: The7DayForecastRepo) {

    // Insert a list of daily forecast data for a specific city into the local database
    suspend fun insertDailyForecastByCityToLocalDataBase(data: List<CurrentWeather>) =
        repo.insertDailyForecastByCityToLocalDataBase(data)

}
