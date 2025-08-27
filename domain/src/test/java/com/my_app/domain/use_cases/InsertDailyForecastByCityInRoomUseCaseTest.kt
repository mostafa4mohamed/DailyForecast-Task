package com.my_app.domain.use_cases


import com.my_app.domain.entities.CurrentWeather
import com.my_app.domain.repo.The7DayForecastRepo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class InsertDailyForecastByCityInRoomUseCaseTest {

    private val repo: The7DayForecastRepo = mockk()
    private val useCase = InsertDailyForecastByCityInRoomUseCase(repo)

    @Test
    fun `test insertDailyForecastByCityToLocalDataBase() calls repo insertDailyForecastByCityToLocalDataBase`() = runBlocking {
        val mockData = listOf(CurrentWeather(city_name = "Cairo"))

        coEvery { repo.insertDailyForecastByCityToLocalDataBase(mockData) } returns Unit

        useCase.insertDailyForecastByCityToLocalDataBase(mockData)

        coVerify { repo.insertDailyForecastByCityToLocalDataBase(mockData) }
    }
}
