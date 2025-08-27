package com.my_app.data.room.base

import com.my_app.data.room.entity.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ConverterHelperTest {

    private lateinit var converter: ConverterHelper

    @Before
    fun setup() {
        converter = ConverterHelper()
    }

    @Test
    fun `CloudsRoomEntity toJson and fromJson should return equal object`() {
        val clouds = CloudsRoomEntity(all = 85)

        val json = converter.fromClouds(clouds)
        val result = converter.toClouds(json)

        assertNotNull(json)
        assertNotNull(result)
        assertEquals(clouds.all, result?.all)
    }

    @Test
    fun `MainRoomEntity toJson and fromJson should return equal object`() {
        val main = MainRoomEntity(
            feels_like = 25.0,
            grnd_level = 1000,
            humidity = 60,
            pressure = 1013,
            sea_level = 1005,
            temp = 26.5,
            temp_kf = 0.2,
            temp_max = 28.0,
            temp_min = 24.0
        )

        val json = converter.fromMain(main)
        val result = converter.toMain(json)

        assertNotNull(json)
        assertNotNull(result)
        result?.temp?.let { assertEquals(main.temp, it, 0.0) }
        assertEquals(main.humidity, result?.humidity)
    }

    @Test
    fun `SysRoomEntity toJson and fromJson should return equal object`() {
        val sys = SysRoomEntity(pod = "d")

        val json = converter.fromSys(sys)
        val result = converter.toSys(json)

        assertNotNull(json)
        assertNotNull(result)
        assertEquals(sys.pod, result?.pod)
    }

    @Test
    fun `WindRoomEntity toJson and fromJson should return equal object`() {
        val wind = WindRoomEntity(deg = 180, gust = 12.0, speed = 5.5)

        val json = converter.fromWind(wind)
        val result = converter.toWind(json)

        assertNotNull(json)
        assertNotNull(result)
        assertEquals(wind.deg, result?.deg)
        result?.speed?.let { assertEquals(wind.speed, it, 0.0) }
    }

    @Test
    fun `WeatherRoomEntity list toJson and fromJson should return equal object`() {
        val list = listOf(
            WeatherRoomEntity("Clear sky", "01d", 800.0, "Clear"),
            WeatherRoomEntity("Cloudy", "02d", 801.0, "Clouds")
        )

        val json = converter.fromListWeather(list)
        val result = converter.toListWeather(json)

        assertNotNull(json)
        assertNotNull(result)
        assertEquals(list.size, result?.size)
        assertEquals(list[0].description, result?.get(0)?.description)
        assertEquals(list[1].id, result?.get(1)?.id)
    }
}
