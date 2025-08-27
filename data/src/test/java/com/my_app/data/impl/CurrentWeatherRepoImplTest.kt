package com.my_app.data.impl

import com.my_app.data.mapper.DailyForecastMapper.toStringData
import com.my_app.data.preferences.UserPreferences
import com.my_app.data.remote.WeatherServices
import com.my_app.domain.entities.CurrentWeather
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class CurrentWeatherRepoImplTest {

    private lateinit var weatherServices: WeatherServices
    private lateinit var userPreferences: UserPreferences
    private lateinit var repo: CurrentWeatherRepoImpl

    @Before
    fun setup() {
        weatherServices = mockk()
        userPreferences = mockk()
        repo = CurrentWeatherRepoImpl(weatherServices, userPreferences)
    }

    @Test
    fun `test getCurrentWeatherByCityFromRemote returns data from API`() = runBlocking {
        val cityName = "Cairo"
        val mockWeather:CurrentWeather = mockk()

        every { mockWeather.city_name } returns cityName
        every { mockWeather.dt } returns 30

        coEvery { weatherServices.getCurrentWeatherByCityFromRemote(cityName) } returns mockWeather

        val result = repo.getCurrentWeatherByCityFromRemote(cityName)

        assertNotNull(result)
        assertEquals(cityName, result.city_name)
        assertEquals(30, result.dt)
    }

    @Test
    fun `test getCurrentWeatherByCityFromLocalDataBase returns data when city matches`() = runBlocking {
        val cityName = "Cairo"
        val mockWeather = CurrentWeather(city_name = cityName)

        every { userPreferences.currentWeather } returns flowOf(mockWeather.toStringData())

        val result = repo.getCurrentWeatherByCityFromLocalDataBase(cityName)

        assertNotNull(result)
        assertEquals(cityName, result?.city_name)
    }

    @Test
    fun `test getCurrentWeatherByCityFromLocalDataBase returns null when city doesn't match`() = runBlocking {
        val cityName = "Cairo"
        val mockWeather = CurrentWeather(city_name = "Alexandria")

        every { userPreferences.currentWeather } returns flowOf(mockWeather.toStringData())

        val result = repo.getCurrentWeatherByCityFromLocalDataBase(cityName)
        println(result)

        assertNull(result)
    }

    @Test
    fun `test insertCurrentWeatherByCityToLocalDataBase saves data correctly`() = runBlocking {

        val mockWeather:CurrentWeather = mockk()

        every { mockWeather.dt } returns 30

        coEvery { userPreferences.saveCurrentWeather(any()) } returns Unit

        repo.insertCurrentWeatherByCityToLocalDataBase(mockWeather)

        coVerify { userPreferences.saveCurrentWeather(mockWeather.toStringData()) }
    }
}
