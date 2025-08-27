package com.my_app.domain.use_cases

import com.my_app.domain.entities.DailyForecastResponse
import com.my_app.domain.repo.The7DayForecastRepo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class Get7DayForecastByCityUseCaseTest {

    private val repo: The7DayForecastRepo = mockk()
    private val useCase = Get7DayForecastByCityUseCase(repo)

    @Test
    fun `test invokeFromRemote calls repo getDailyForecastByCityFromRemote`() = runBlocking {
        val cityName = "Cairo"
        val mockResponse = DailyForecastResponse(list = emptyList())

        coEvery { repo.getDailyForecastByCityFromRemote(cityName) } returns mockResponse

        val result = useCase.invokeFromRemote(cityName)

        assertEquals(mockResponse, result)

        coVerify { repo.getDailyForecastByCityFromRemote(cityName) }
    }

    @Test
    fun `test invokeFromLocalData calls repo getDailyForecastByCityFromLocalDataBase`() = runBlocking {
        val cityName = "Cairo"
        val mockResponse = DailyForecastResponse(list = emptyList())

        coEvery { repo.getDailyForecastByCityFromLocalDataBase(cityName) } returns mockResponse

        val result = useCase.invokeFromLocalData(cityName)

        assertEquals(mockResponse, result)

        coVerify { repo.getDailyForecastByCityFromLocalDataBase(cityName) }
    }
}
