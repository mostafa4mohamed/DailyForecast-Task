package com.my_app.daily_forecast.ui.screens.home_screen.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my_app.domain.entities.CurrentWeather
import com.my_app.domain.entities.DailyForecastResponse
import com.my_app.domain.use_cases.CurrentWeatherUseCase
import com.my_app.domain.use_cases.Get7DayForecastByCityUseCase
import com.my_app.domain.use_cases.InsertDailyForecastByCityInRoomUseCase
import com.my_app.domain.use_cases.LastCityUseCase
import com.my_app.daily_forecast.state.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val get7DayForecastByCityUseCase: Get7DayForecastByCityUseCase,
    private val insertDailyForecastByCityInRoomUseCase: InsertDailyForecastByCityInRoomUseCase,
    private val lastCityUseCase: LastCityUseCase,
    private val getCurrentWeatherByCityUseCase: CurrentWeatherUseCase,
) :
    ViewModel() {

    val forecastState = MutableStateFlow<ForecastState>(ForecastState.Idle)
    private val forecastIntent = MutableStateFlow<ForecastIntent>(ForecastIntent.Idle)

    private val _getCurrentWeatherStateFlow =
        MutableStateFlow<NetworkState<CurrentWeather?>>(NetworkState.Idle)
    val getCurrentWeatherStateFlow get() = _getCurrentWeatherStateFlow

    var inputText = mutableStateOf("")
        private set
    var cityInput = mutableStateOf("")
        private set

    init {
        viewModelScope.launch {

            handleForecastIntent()

            val lastCity = lastCityUseCase.getLastCity() ?: ""
            inputText.value = lastCity
            cityInput.value = lastCity

            forecastIntent.emit(ForecastIntent.LoadForecast(lastCity))
            loadInitialDataOfCurrentWeather()
        }
    }

    private fun handleForecastIntent() {
        viewModelScope.launch {
            forecastIntent.collect { intent ->
                when (intent) {
                    is ForecastIntent.LoadForecast -> {
                        loadInitialDataOf7DaysForecast(intent.city)
                    }

                    ForecastIntent.Idle -> {
                        // Do nothing
                    }
                }
            }
        }
    }

    private fun loadInitialDataOf7DaysForecast(cityNameRaw: String) {
        val cityName = cityNameRaw.lowercase().trim()
        if (cityName.isEmpty()) return

        forecastState.value = ForecastState.Loading

        viewModelScope.launch {
            saveLastCity(cityName)

            kotlin.runCatching {
                get7DayForecastByCityUseCase.invokeFromRemote(cityName)
            }.onFailure { th ->
                try {
                    val localData = invoke7DaysForecastFromRoom(cityName)
                    if (localData?.list.isNullOrEmpty())
                        forecastState.value = ForecastState.Error(th.message)
                    else
                        forecastState.value = ForecastState.Success(localData, false)
                } catch (_: Exception) {
                    forecastState.value = ForecastState.Error(th.message)
                }
            }.onSuccess { remoteData ->
                val data: DailyForecastResponse?
                val isOnline: Boolean

                if (remoteData != null) {
                    isOnline = true
                    save7DaysForecastAtRoom(cityName, remoteData.list)
                    data = remoteData
                } else {
                    try {
                        isOnline = false
                        data = invoke7DaysForecastFromRoom(cityName)
                    } catch (e: Exception) {
                        forecastState.value = ForecastState.Error(e.message)
                        return@onSuccess
                    }
                }

                forecastState.value = ForecastState.Success(data, isOnline)
            }
        }
    }

    private fun loadInitialDataOfCurrentWeather() {

        val cityName = inputText.value.lowercase().trim()
        if (cityName.isEmpty()) return

        _getCurrentWeatherStateFlow.value = NetworkState.Loading

        viewModelScope.launch {
            try {
                val data = invokeCurrentWeatherFromLocalDatabase(cityName)
                if (data != null)
                    _getCurrentWeatherStateFlow.value = NetworkState.Result(data, false)
                else
                    _getCurrentWeatherStateFlow.value = NetworkState.Idle

            } catch (_: Exception) {
            }
        }

    }

    fun getCurrentWeather() {

        val cityName = inputText.value.lowercase().trim()
        _getCurrentWeatherStateFlow.value = NetworkState.Loading

        viewModelScope.launch {
            kotlin.runCatching {
                getCurrentWeatherByCityUseCase.invokeFromRemote(cityName)
            }.onFailure { th ->
                try {
                    val data = getCurrentWeatherByCityUseCase.invokeFromLocalData(cityName)
                    if (data == null)
                        _getCurrentWeatherStateFlow.value = NetworkState.Error(msg = th.message)
                    else
                        _getCurrentWeatherStateFlow.value = NetworkState.Result(data, false)

                } catch (_: Exception) {
                    _getCurrentWeatherStateFlow.value = NetworkState.Error(msg = th.message)
                }
            }.onSuccess {

                val isOnline: Boolean
                val data: CurrentWeather?

                if (it != null) {
                    isOnline = true
                    saveCurrentWeatherAtLocalDatabase(cityName, it)
                    data = it
                } else {

                    try {
                        isOnline = false
                        data = invokeCurrentWeatherFromLocalDatabase(cityName)

                    } catch (e: Exception) {
                        _getCurrentWeatherStateFlow.value = NetworkState.Error(msg = e.message)
                        return@onSuccess
                    }

                }
                _getCurrentWeatherStateFlow.value = NetworkState.Result(data, isOnline)

            }
        }

    }

    fun get7DaysForecastByCity() {

        val cityName = cityInput.value.lowercase().trim()
        viewModelScope.launch {
            saveLastCity(cityName)
        }

        forecastState.value = ForecastState.Loading

        viewModelScope.launch {
            kotlin.runCatching {
                get7DayForecastByCityUseCase.invokeFromRemote(
                    cityName
                )
            }.onFailure { th ->
                try {
                    val data = invoke7DaysForecastFromRoom(cityName)
                    if (data?.list.isNullOrEmpty())
                        forecastState.value = ForecastState.Error(th.message)
                    else
                        forecastState.value = ForecastState.Success(data, false)

                } catch (_: Exception) {
                    forecastState.value = ForecastState.Error(th.message)
                }
            }.onSuccess {

                val isOnline: Boolean
                val data: DailyForecastResponse?

                if (it != null) {
                    isOnline = true
                    save7DaysForecastAtRoom(cityName, it.list)
                    data = it
                } else {

                    try {
                        isOnline = false
                        data = invoke7DaysForecastFromRoom(cityName)

                    } catch (e: Exception) {
                        forecastState.value = ForecastState.Error(e.message)
                        return@onSuccess
                    }

                }

                forecastState.value = ForecastState.Success(data, isOnline)

            }
        }
    }

    private suspend fun saveLastCity(cityName: String) {
        lastCityUseCase.saveLastCity(cityName)
    }

    private suspend fun save7DaysForecastAtRoom(cityName: String, list: List<CurrentWeather>?) {

        if (list.isNullOrEmpty()) return

        val data: List<CurrentWeather> = list.map {
            it.city_name = cityName
            it
        }

        insertDailyForecastByCityInRoomUseCase.insertDailyForecastByCityToLocalDataBase(data)
    }

    private suspend fun saveCurrentWeatherAtLocalDatabase(cityName: String, data: CurrentWeather) {


        data.apply {
            city_name = cityName
        }

        getCurrentWeatherByCityUseCase.insertToLocalDataBase(data)

    }

    private suspend fun invoke7DaysForecastFromRoom(cityName: String) =
        get7DayForecastByCityUseCase.invokeFromLocalData(cityName)

    private suspend fun invokeCurrentWeatherFromLocalDatabase(cityName: String) =
        getCurrentWeatherByCityUseCase.invokeFromLocalData(cityName)

    fun sendIntent(intent: ForecastIntent) {
        viewModelScope.launch {
            forecastIntent.emit(intent)
        }
    }

}