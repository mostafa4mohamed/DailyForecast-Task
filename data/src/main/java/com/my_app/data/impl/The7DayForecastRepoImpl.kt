package com.my_app.data.impl

import com.my_app.data.local.DailyForecastDao
import com.my_app.data.mapper.DailyForecastMapper.toDomainEntity
import com.my_app.data.mapper.DailyForecastMapper.toRoomEntity
import com.my_app.data.remote.WeatherServices
import com.my_app.domain.entities.CurrentWeather
import com.my_app.domain.entities.DailyForecastResponse
import com.my_app.domain.repo.The7DayForecastRepo

class The7DayForecastRepoImpl(
    private val apis: WeatherServices,
    private val dao: DailyForecastDao
) : The7DayForecastRepo {

    // Fetch 7-day weather forecast from remote API
    override suspend fun getDailyForecastByCityFromRemote(
        cityName: String
    ): DailyForecastResponse =
        // Call to the weather service API to get forecast data
        apis.get7DayForecastByCityFromRemote(cityName)

    // Fetch 7-day weather forecast from local database
    override suspend fun getDailyForecastByCityFromLocalDataBase(
        cityName: String
    ): DailyForecastResponse {
        // Fetch data from the database and map it to domain entities
        val forecastList = dao.getDailyForecastByCity(cityName).map { it.toDomainEntity() }
        return DailyForecastResponse(list = forecastList)
    }

    // Insert 7-day weather forecast into local database
    override suspend fun insertDailyForecastByCityToLocalDataBase(data: List<CurrentWeather>) {
        // Map data to room entities and insert into the database
        dao.insert(data.map { it.toRoomEntity() })
    }
}
