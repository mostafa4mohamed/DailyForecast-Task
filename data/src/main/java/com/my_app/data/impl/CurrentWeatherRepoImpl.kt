package com.my_app.data.impl

import com.my_app.data.mapper.DailyForecastMapper.toDataClass
import com.my_app.data.mapper.DailyForecastMapper.toStringData
import com.my_app.data.preferences.UserPreferences
import com.my_app.data.remote.WeatherServices
import com.my_app.domain.entities.CurrentWeather
import com.my_app.domain.repo.CurrentWeatherRepo
import kotlinx.coroutines.flow.first

class CurrentWeatherRepoImpl(
    private val apis: WeatherServices,
    private val userPreferences: UserPreferences
) : CurrentWeatherRepo {

    // Fetch current weather from remote service
    override suspend fun getCurrentWeatherByCityFromRemote(cityName: String) =
        apis.getCurrentWeatherByCityFromRemote(cityName)

    // Fetch current weather from local database
    override suspend fun getCurrentWeatherByCityFromLocalDataBase(
        cityName: String,
    ): CurrentWeather? {

        val savedWeather = userPreferences.currentWeather.first()
        val weatherData = savedWeather?.toDataClass()

        // Check if the saved data matches the requested city
        return if (weatherData?.city_name == cityName) {
            weatherData
        } else {
            null
        }
    }

    // Save current weather data to local database
    override suspend fun insertCurrentWeatherByCityToLocalDataBase(data: CurrentWeather) =
        userPreferences.saveCurrentWeather(data.toStringData())
}
