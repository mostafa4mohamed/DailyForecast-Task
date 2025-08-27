package com.my_app.data.impl

import com.my_app.data.preferences.UserPreferences
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class LastCityRepoImplTest {

    private val userPreferences = mockk<UserPreferences>()
    private val repo = LastCityRepoImpl(userPreferences)

    @Test
    fun `test getLastCity returns city when city is saved`() = runBlocking {
        val cityName = "Cairo"

        every { userPreferences.lastCityName } returns flowOf(cityName)
        val result = repo.getLastCity()

        assertEquals(cityName, result)
    }

    @Test
    fun `test getLastCity returns null when no city is saved`() = runBlocking {
        every { userPreferences.lastCityName } returns flowOf(null)

        val result = repo.getLastCity()

        assertEquals(null, result)
    }

    @Test
    fun `test saveLastCity saves city successfully`() = runBlocking {
        val cityName = "Cairo"

        coEvery { userPreferences.saveLastCityName(cityName) } returns Unit

        repo.saveLastCity(cityName)

        verify { runBlocking{ userPreferences.saveLastCityName(cityName) } }
    }

}
