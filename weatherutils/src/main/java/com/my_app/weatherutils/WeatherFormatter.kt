package com.my_app.weatherutils

import java.util.Locale

object WeatherFormatter {

    fun Double.formatTemperatureToCelsius(): String {
        return "${this.convertFromKelvinToCelsius().format2Digits()} ℃"
    }
    private fun Double.convertFromKelvinToCelsius(): Double = this - 273.15

    private fun Double?.format2Digits(): String =
        if (this == null) ""
        else String.format(Locale.ENGLISH, "%.2f", this)

    fun String.getFileImageUrl() = "https://openweathermap.org/img/wn/$this@2x.png"

}
