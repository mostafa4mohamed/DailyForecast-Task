package com.my_app.domain.repo


interface LastCityRepo {

    suspend  fun getLastCity(): String?

    suspend fun saveLastCity(cityName: String)

}