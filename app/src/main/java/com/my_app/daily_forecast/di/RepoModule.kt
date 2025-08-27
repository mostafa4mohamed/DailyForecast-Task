package com.my_app.daily_forecast.di

import com.my_app.data.impl.CurrentWeatherRepoImpl
import com.my_app.data.impl.The7DayForecastRepoImpl
import com.my_app.data.impl.LastCityRepoImpl
import com.my_app.data.local.DailyForecastDao
import com.my_app.data.preferences.UserPreferences
import com.my_app.data.remote.WeatherServices
import com.my_app.domain.repo.CurrentWeatherRepo
import com.my_app.domain.repo.The7DayForecastRepo
import com.my_app.domain.repo.LastCityRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideGetDailyForecastRepo(
        apiServices: WeatherServices,
        dao: DailyForecastDao
    ): The7DayForecastRepo =
        The7DayForecastRepoImpl(apiServices, dao)

    @Provides
    fun provideLastCityRepo(userPreferences: UserPreferences): LastCityRepo =
        LastCityRepoImpl(userPreferences)

    @Provides
    fun provideCurrentWeatherRepo(
        apiServices: WeatherServices,
        userPreferences: UserPreferences
    ): CurrentWeatherRepo = CurrentWeatherRepoImpl(apiServices, userPreferences)

}