package com.my_app.domain.use_cases

import com.my_app.domain.repo.LastCityRepo

class LastCityUseCase(private val repo: LastCityRepo) {


    suspend fun getLastCity(): String? = repo.getLastCity()


    suspend fun saveLastCity(cityName: String) = repo.saveLastCity(cityName)


}