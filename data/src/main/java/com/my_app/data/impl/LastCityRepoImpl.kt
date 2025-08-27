package com.my_app.data.impl

import com.my_app.data.preferences.UserPreferences
import com.my_app.domain.repo.LastCityRepo
import kotlinx.coroutines.flow.first

class LastCityRepoImpl(private val userPreferences: UserPreferences) : LastCityRepo {

    // Fetch the last saved city from user preferences
    override suspend fun getLastCity(): String? {
        // Safely retrieve the last city name
        return userPreferences.lastCityName.first()
    }

    // Save the last city name to user preferences
    override suspend fun saveLastCity(cityName: String) {
        userPreferences.saveLastCityName(cityName)
    }
}
