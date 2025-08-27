package com.my_app.daily_forecast.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.my_app.data.local.CurrentWeatherDao
import com.my_app.data.local.DailyForecastDao
import com.my_app.data.preferences.UserPreferences
import com.my_app.data.preferences.UserPreferences.Companion.PREFERENCES_STORE_NAME
import com.my_app.data.room.base.RoomManger
import com.my_app.data.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {


    @Singleton
    @Provides
    fun provideMainDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        RoomManger::class.java,
        Constants.DB_NAME
    )
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideDailyForecastDao(db: RoomManger): DailyForecastDao = db.dailyForecastDao()

    @Singleton
    @Provides
    fun provideCurrentWeatherDao(db: RoomManger): CurrentWeatherDao = db.currentWeatherDao()

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext app: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                app.preferencesDataStoreFile(PREFERENCES_STORE_NAME)
            })

    @Singleton
    @Provides
    fun provideUserPreferences(dataStore: DataStore<Preferences>): UserPreferences =
        UserPreferences(dataStore)

}