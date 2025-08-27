package com.my_app.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserPreferencesTest {

    private lateinit var userPreferences: UserPreferences
    private val mockDataStore = mockk<DataStore<Preferences>>(relaxed = true)

    private val lastCityKey = stringPreferencesKey("last_city")
    private val currentWeatherKey = stringPreferencesKey("current_weather")

    @Before
    fun setup() {
        userPreferences = UserPreferences(mockDataStore)
    }

    @Test
    fun `lastCityName should emit correct value`() = runBlocking {
        val expected = "Alex"
        val prefs = emptyPreferences().toMutablePreferences().apply {
            this[lastCityKey] = expected
        }

        coEvery { mockDataStore.data } returns flowOf(prefs)

        val result = userPreferences.lastCityName
        result.collect {
            assertEquals(expected, it)
        }
    }

    @Test
    fun `currentWeather should emit correct value`() = runBlocking {
        val expected = "Sunny"
        val prefs = emptyPreferences().toMutablePreferences().apply {
            this[currentWeatherKey] = expected
        }

        coEvery { mockDataStore.data } returns flowOf(prefs)

        val result = userPreferences.currentWeather
        result.collect {
            assertEquals(expected, it)
        }
    }
}
