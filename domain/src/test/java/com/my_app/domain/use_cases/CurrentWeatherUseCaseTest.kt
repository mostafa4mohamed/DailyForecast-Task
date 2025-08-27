package com.my_app.domain.use_cases

import com.my_app.domain.entities.CurrentWeather
import com.my_app.domain.repo.CurrentWeatherRepo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrentWeatherUseCaseTest {

    private lateinit var repo: CurrentWeatherRepo
    private lateinit var useCase: CurrentWeatherUseCase

    @Before
    fun setup() {
        repo = mockk()
        useCase = CurrentWeatherUseCase(repo)
    }

    @Test
    fun `test invokeFromRemote() calls repo getCurrentWeatherByCityFromRemote`() = runBlocking {
        val cityName = "Cairo"
        val mockWeather = CurrentWeather(city_name = cityName)

        coEvery { repo.getCurrentWeatherByCityFromRemote(cityName) } returns mockWeather

        val result = useCase.invokeFromRemote(cityName)

        coVerify { repo.getCurrentWeatherByCityFromRemote(cityName) }
        assertEquals(mockWeather, result)
    }

    @Test
    fun `test invokeFromLocalData() calls repo getCurrentWeatherByCityFromLocalDataBase`() = runBlocking {
        val cityName = "Cairo"
        val mockWeather = CurrentWeather(city_name = cityName)

        coEvery { repo.getCurrentWeatherByCityFromLocalDataBase(cityName) } returns mockWeather

        val result = useCase.invokeFromLocalData(cityName)

        coVerify { repo.getCurrentWeatherByCityFromLocalDataBase(cityName) }
        assertEquals(mockWeather, result)
    }

    @Test
    fun `test insertToLocalDataBase() calls repo insertCurrentWeatherByCityToLocalDataBase`() = runBlocking {
        val cityName = "Cairo"
        val mockWeather = CurrentWeather(city_name = cityName)

        coEvery { repo.insertCurrentWeatherByCityToLocalDataBase(mockWeather) } returns Unit

        // Call the use case
        useCase.insertToLocalDataBase(mockWeather)

        // Verify that the correct repository method was called
        coVerify { repo.insertCurrentWeatherByCityToLocalDataBase(mockWeather) }
    }
}
