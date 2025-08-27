package com.my_app.daily_forecast.utils
import com.my_app.daily_forecast.utils.DateUtils.dateFormat
import com.my_app.daily_forecast.utils.DateUtils.isToday
import org.junit.Assert.*
import org.junit.Test

class DateUtilsTest {

    @Test
    fun `dateFormat should return formatted date when input is full format`() {
        val input = "2025-08-27 15:30:00"
        val result = input.dateFormat()
        assertEquals("27/08/2025 03:30 PM", result)
    }

    @Test
    fun `dateFormat should return formatted date when input is short format`() {
        val input = "27/08/2025 03:30 PM"
        val result = input.dateFormat()
        assertNull( result)
    }

    @Test
    fun `dateFormat should return null when input is invalid`() {
        val input = "invalid-date"
        val result = input.dateFormat()
        assertNull(result)
    }

    @Test
    fun `isToday should return true for today's timestamp`() {
        val now = (System.currentTimeMillis() / 1000).toInt()
        assertTrue(now.isToday())
    }

    @Test
    fun `isToday should return false for yesterday's timestamp`() {
        val yesterday = ((System.currentTimeMillis() - 24*60*60*1000) / 1000).toInt()
        assertFalse(yesterday.isToday())
    }
}
