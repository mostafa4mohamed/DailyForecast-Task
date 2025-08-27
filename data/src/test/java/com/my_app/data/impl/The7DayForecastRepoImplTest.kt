package com.my_app.data.impl

import com.my_app.data.local.DailyForecastDao
import com.my_app.data.mapper.DailyForecastMapper.toDomainEntity
import com.my_app.data.mapper.DailyForecastMapper.toRoomEntity
import com.my_app.data.remote.WeatherServices
import com.my_app.domain.entities.CurrentWeather
import com.my_app.domain.entities.DailyForecastResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class The7DayForecastRepoImplTest {

    private val weatherServices: WeatherServices = mockk()
    private val dailyForecastDao: DailyForecastDao = mockk()
    private val repo = The7DayForecastRepoImpl(weatherServices, dailyForecastDao)

    @Test
    fun `test getDailyForecastByCityFromRemote`() = runBlocking {
        val cityName = "Cairo"
        val mockResponse = DailyForecastResponse(list = listOf(CurrentWeather(city_name = cityName)))

        coEvery { weatherServices.get7DayForecastByCityFromRemote(cityName) } returns mockResponse

        val result = repo.getDailyForecastByCityFromRemote(cityName)
        assertEquals(mockResponse, result)

        coVerify { weatherServices.get7DayForecastByCityFromRemote(cityName) }
    }

    @Test
    fun `test getDailyForecastByCityFromLocalDataBase`() = runBlocking {
        val cityName = "Cairo"
        val mockForecastList = listOf(CurrentWeather(city_name = cityName).toRoomEntity())
        val mockResponse = DailyForecastResponse(list = mockForecastList.map { it.toDomainEntity() })

        coEvery { dailyForecastDao.getDailyForecastByCity(cityName) } returns mockForecastList

        val result = repo.getDailyForecastByCityFromLocalDataBase(cityName)

        assertEquals(mockResponse, result)

        coVerify { dailyForecastDao.getDailyForecastByCity(cityName) }
    }

    @Test
    fun `test insertDailyForecastByCityToLocalDataBase`() = runBlocking {
        val mockData = listOf(CurrentWeather(city_name = "Cairo"))

        coEvery { dailyForecastDao.insert(any()) } returns Unit

        repo.insertDailyForecastByCityToLocalDataBase(mockData)

        coVerify { dailyForecastDao.insert(mockData.map { it.toRoomEntity() }) }
    }
}
