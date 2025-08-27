package com.my_app.data.mapper

import com.my_app.data.mapper.DailyForecastMapper.toDataClass
import com.my_app.data.mapper.DailyForecastMapper.toDomainEntity
import com.my_app.data.mapper.DailyForecastMapper.toRoomEntity
import com.my_app.data.mapper.DailyForecastMapper.toStringData
import com.my_app.data.room.entity.*
import com.my_app.domain.entities.*
import org.junit.Assert.*
import org.junit.Test

class DailyForecastMapperTest {

    @Test
    fun `CurrentWeather maps to DailyForecastRoomEntity correctly`() {
        // Arrange
        val domain = CurrentWeather(
            city_name = "Cairo",
            clouds = Clouds(80),
            dt = 123456789,
            dt_txt = "2025-08-27 12:00:00",
            main = Main(30.0, 1000, 50, 1013, 1010, 31.0, 0.5, 32.0, 29.0),
            pop = 0.2f,
            sys = Sys("d"),
            visibility = 10000,
            weather = listOf(
                Weather("Clear sky", "01d", 800.0, "Clear")
            ),
            wind = Wind(120, 5.5, 10.0)
        )

        // Act
        val roomEntity = domain.toRoomEntity()

        // Assert
        assertEquals("Cairo", roomEntity.city_name)
        assertEquals(80, roomEntity.clouds?.all)
        assertEquals(123456789, roomEntity.dt)
        assertEquals("2025-08-27 12:00:00", roomEntity.dt_txt)
        roomEntity.main?.feels_like?.let { assertEquals(30.0, it, 0.0) }
        assertEquals("Clear sky", roomEntity.weather?.first()?.description)
        assertEquals(120, roomEntity.wind?.deg)
    }

    @Test
    fun `DailyForecastRoomEntity maps to CurrentWeather correctly`() {
        // Arrange
        val room = DailyForecastRoomEntity(
//            id = 1,
            city_name = "Cairo",
            clouds = CloudsRoomEntity(75),
            dt = 987654321,
            dt_txt = "2025-08-27 15:00:00",
            main = MainRoomEntity(28.0, 1001, 60, 1015, 1012, 29.0, 0.2, 30.0, 27.0),
            pop = 0.5f,
            sys = SysRoomEntity("n"),
            visibility = 9000,
            weather = listOf(
                WeatherRoomEntity("Few clouds", "02n", 801.0, "Clouds")
            ),
            wind = WindRoomEntity(200, 8.0, 15.0)
        )

        // Act
        val domain = room.toDomainEntity()

        // Assert
        assertEquals("Cairo", domain.city_name)
        assertEquals(75, domain.clouds?.all)
        assertEquals(987654321, domain.dt)
        assertEquals("2025-08-27 15:00:00", domain.dt_txt)
        domain.main?.feels_like?.let { assertEquals(28.0, it, 0.0) }
        assertEquals("Few clouds", domain.weather?.first()?.description)
        assertEquals(200, domain.wind?.deg)
    }

    @Test
    fun `CurrentWeather toStringData and toDataClass should serialize and deserialize correctly`() {
        // Arrange
        val original = CurrentWeather(
            city_name = "Alexandria",
            clouds = Clouds(60),
            dt = 123456789,
            dt_txt = "2025-08-27 18:00:00",
            main = Main(25.0, 1005, 70, 1012, 1008, 26.0, 0.3, 27.0, 24.0),
            pop = 0.1f,
            sys = Sys("d"),
            visibility = 8000,
            weather = listOf(
                Weather("Sunny", "01d", 800.0, "Clear")
            ),
            wind = Wind(90, 3.0, 7.0)
        )

        // Act
        val jsonString = original.toStringData()
        val deserialized = jsonString.toDataClass()

        // Assert
        assertNotNull(deserialized)
        assertEquals(original.city_name, deserialized?.city_name)
        assertEquals(original.main?.temp, deserialized?.main?.temp)
        assertEquals(original.weather?.first()?.id, deserialized?.weather?.first()?.id)
    }
}
