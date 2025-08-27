package com.my_app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.my_app.data.room.entity.DailyForecastRoomEntity

@Dao
interface DailyForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: List<DailyForecastRoomEntity>)

    @Query("SELECT * FROM DailyForecastRoomEntity WHERE city_name = :cityName")
    suspend fun getDailyForecastByCity(cityName:String): List<DailyForecastRoomEntity>
}