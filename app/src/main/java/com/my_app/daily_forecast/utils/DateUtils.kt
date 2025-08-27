package com.my_app.daily_forecast.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateUtils {

    private val shortDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
    private val fullDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)

    fun String?.dateFormat(): String? {

        if (this.isNullOrEmpty()) return null

        val parsed = try {
            fullDateFormat.parse(this)
        }catch (e:ParseException){
            return null
        } catch (e: Exception) {
            shortDateFormat.parse(this)
        }

        return shortDateFormat.format(parsed!!)
    }

    private const val DATE_PATTERN = "dd-MM-yyyy"

    fun Int.isToday(): Boolean {
        return try {
            val inputDate = Calendar.getInstance()
            inputDate.timeInMillis = this.toLong() * 1000

            val today = Calendar.getInstance()

            val formatter = SimpleDateFormat(DATE_PATTERN, Locale.ENGLISH)

            val formattedInput = formatter.format(inputDate.time)
            val formattedToday = formatter.format(today.time)

            formattedInput == formattedToday

        } catch (e: Exception) {
            false
        }
    }

}
