package com.my_app.weatherutils

import com.my_app.weatherutils.WeatherFormatter.formatTemperatureToCelsius
import com.my_app.weatherutils.WeatherFormatter.getFileImageUrl
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherFormatterTest {

    @Test
    fun `formatTemperatureToCelsius should convert Kelvin to Celsius with 2 decimal places`() {
        val kelvin = 300.0
        val expected = "26.85 ℃"
        val actual = kelvin.formatTemperatureToCelsius()
        assertEquals(expected, actual)
    }

    @Test
    fun `getFileImageUrl should return correct URL`() {
        val iconId = "10d"
        val expectedUrl = "https://openweathermap.org/img/wn/10d@2x.png"
        val actualUrl = iconId.getFileImageUrl()
        assertEquals(expectedUrl, actualUrl)
    }
}
