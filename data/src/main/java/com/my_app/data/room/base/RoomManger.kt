package com.my_app.data.room.base

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.my_app.data.local.CurrentWeatherDao
import com.my_app.data.local.DailyForecastDao
import com.my_app.data.room.entity.DailyForecastRoomEntity

@Database(
    entities = [DailyForecastRoomEntity::class],
    version = 3,
    exportSchema = false,
)
@TypeConverters(ConverterHelper::class)
abstract class RoomManger : RoomDatabase() {

    abstract fun dailyForecastDao(): DailyForecastDao

    abstract fun currentWeatherDao(): CurrentWeatherDao

}