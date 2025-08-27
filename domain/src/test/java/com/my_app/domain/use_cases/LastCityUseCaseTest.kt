package com.my_app.domain.use_cases


import com.my_app.domain.repo.LastCityRepo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class LastCityUseCaseTest {

    private val repo: LastCityRepo = mockk()
    private val useCase = LastCityUseCase(repo)

    @Test
    fun `test getLastCity() returns the correct city`() = runBlocking {
        val expectedCity = "Cairo"
        coEvery { repo.getLastCity() } returns expectedCity
        val result = useCase.getLastCity()
        assertEquals(expectedCity, result)
    }

    @Test
    fun `test saveLastCity() calls repo saveLastCity`() = runBlocking {
        val cityName = "Cairo"
        coEvery { repo.saveLastCity(cityName) } returns Unit
        useCase.saveLastCity(cityName)
        coVerify { repo.saveLastCity(cityName) }
    }
}
