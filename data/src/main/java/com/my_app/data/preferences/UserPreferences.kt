package com.my_app.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
    private val dataStore: DataStore<Preferences>
) {

    val lastCityName: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[LAST_CITY]
        }

    suspend fun saveLastCityName(cityName: String?) {
        dataStore.edit { preferences ->
            preferences[LAST_CITY] = cityName ?: ""
        }
    }

    val currentWeather: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[CURRENT_WEATHER]
        }

    suspend fun saveCurrentWeather(data: String?) {
        dataStore.edit { preferences ->
            preferences[CURRENT_WEATHER] = data ?: ""
        }
    }

    companion object {
        const val PREFERENCES_STORE_NAME = "w_store"
        private val LAST_CITY = stringPreferencesKey("last_city")
        private val CURRENT_WEATHER = stringPreferencesKey("current_weather")
    }

}