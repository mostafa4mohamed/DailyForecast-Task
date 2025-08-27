package com.my_app.daily_forecast.di

import com.my_app.domain.repo.CurrentWeatherRepo
import com.my_app.domain.repo.The7DayForecastRepo
import com.my_app.domain.repo.LastCityRepo
import com.my_app.domain.use_cases.CurrentWeatherUseCase
import com.my_app.domain.use_cases.Get7DayForecastByCityUseCase
import com.my_app.domain.use_cases.InsertDailyForecastByCityInRoomUseCase
import com.my_app.domain.use_cases.LastCityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetDailyForecastUseCase(repo: The7DayForecastRepo) =
        Get7DayForecastByCityUseCase(repo)

    @Provides
    fun provideInsertDailyForecastByCityInRoomUseCase(repo: The7DayForecastRepo) =
        InsertDailyForecastByCityInRoomUseCase(repo)

    @Provides
    fun provideLastCityUseCase(repo: LastCityRepo) =
        LastCityUseCase(repo)

    @Provides
    fun provideGetCurrentWeatherByCityUseCase(repo: CurrentWeatherRepo) =
        CurrentWeatherUseCase(repo)
}