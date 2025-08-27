package com.my_app.daily_forecast.ui.screens.home_screen.vm

import com.my_app.domain.entities.DailyForecastResponse

sealed class ForecastState {
    object Idle : ForecastState()
    object Loading : ForecastState()
    data class Success(val response: DailyForecastResponse?, val isOnline: Boolean) : ForecastState()
    data class Error(val message: String?) : ForecastState()
}
