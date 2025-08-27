package com.my_app.data.local

import androidx.room.Dao
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.my_app.data.room.entity.DailyForecastRoomEntity

@Dao
interface CurrentWeatherDao {

    @androidx.room.Insert(onConflict = REPLACE)
    suspend fun insert(user: DailyForecastRoomEntity)

    @androidx.room.Query("SELECT * FROM DailyForecastRoomEntity WHERE city_name = :cityName")
    suspend fun getCurrentWeatherByCity(cityName: String): DailyForecastRoomEntity

}