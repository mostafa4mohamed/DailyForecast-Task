package com.my_app.daily_forecast.ui.screens.home_screen.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.my_app.domain.entities.CurrentWeather
import com.my_app.daily_forecast.R
import com.my_app.daily_forecast.state.NetworkState
import com.my_app.daily_forecast.ui.screens.home_screen.vm.ForecastState
import com.my_app.daily_forecast.ui.screens.home_screen.vm.HomeViewModel
import com.my_app.daily_forecast.utils.DateUtils.dateFormat
import com.my_app.daily_forecast.utils.DateUtils.isToday
import com.my_app.weatherutils.WeatherFormatter.formatTemperatureToCelsius

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeContent(
    viewModel: HomeViewModel,
    onButtonClick: () -> Unit
) {

    val inputText = viewModel.inputText

    val backgroundColor = MaterialTheme.colorScheme.background

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

        SearchBar(inputText, onButtonClick)

        CurrentWeatherUI(viewModel)

        ListOf7DaysForecastUI(viewModel)

        HorizontalDivider(modifier = Modifier.padding(horizontal = dimensionResource(id = com.intuit.sdp.R.dimen._14sdp)))
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ListOf7DaysForecastUI(viewModel: HomeViewModel) {

    if (viewModel.forecastState.value is ForecastState.Success) {

        val resultState =
            viewModel.forecastState.collectAsStateWithLifecycle().value as ForecastState.Success

        HintText()

        CheckOnlineState(
            resultState = resultState,
            viewModel = viewModel
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = com.intuit.sdp.R.dimen._6sdp))
        ) {

            if (resultState.response?.list.isNullOrEmpty()) return@LazyColumn

            Log.e("TAG", "HomeContent: ${resultState.response}")

            items(resultState.response!!.list!!) { data ->

                ItemOfListContent(data, showDate = false)

            }
        }
    }
}

@Composable
fun HintText() {

    val hint = stringResource(id = R.string._7_day_forecast)

    Text(
        text = hint,
        color = colorResource(id = R.color.teal_200),
        fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._12ssp).value.sp,
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = com.intuit.sdp.R.dimen._18sdp))
            .padding(top = dimensionResource(id = com.intuit.sdp.R.dimen._8sdp))
    )
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CurrentWeatherUI(viewModel: HomeViewModel) {

    if (viewModel.getCurrentWeatherStateFlow.value is NetworkState.Result) {

        val resultState =
            viewModel.getCurrentWeatherStateFlow.collectAsState().value as NetworkState.Result

        val isTodayDate=resultState.response?.dt?.isToday() ?: false

        if (!isTodayDate) return

        Text(
            text = stringResource(R.string.current_weather),
            color = colorResource(id = R.color.teal_200),
            fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._12ssp).value.sp,
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = com.intuit.sdp.R.dimen._14sdp))
                .padding(top = dimensionResource(id = com.intuit.sdp.R.dimen._7sdp))
        )

        ItemOfListContent(
            data = resultState.response!!, backgroundColor = colorResource(id = R.color.purple_20),showDate = false
        )

    }
}

@Composable
fun CheckOnlineState(
    resultState: ForecastState.Success,
    viewModel: HomeViewModel
) {
    if (!resultState.isOnline) {

        val cityName = viewModel.cityInput.value

        val hint = if (cityName.isEmpty())
            stringResource(id = R.string.it_is_not_accurate_data)
        else
            stringResource(id = R.string.it_is_not_accurate_data) +
                    " ${stringResource(id = R.string._for)} " +
                    cityName

        Text(
            text = hint,
            color = Color.Red,
            fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._10ssp).value.sp,
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = com.intuit.sdp.R.dimen._18sdp))
        )
    }
}

@Composable
private fun ItemOfListContent(
    data: CurrentWeather,
    backgroundColor: Color = colorResource(id = R.color.purple_2),
    showDate: Boolean=true
) {
    val date = "${data.dt_txt.dateFormat()}"
    val temp = data.main?.temp?.formatTemperatureToCelsius() ?: ""
    val humidity = "${data.main?.humidity} %"
    val description = if (!data.weather.isNullOrEmpty())
        data.weather?.first()?.description ?: ""
    else ""
    val windSpeed = "${data.wind?.speed} k/hr"
    val icon = data.weather?.firstOrNull()?.icon ?: ""

    WeatherInfoCard(
        date = date,
        temp = temp,
        humidity = humidity,
        description = description,
        windSpeed = windSpeed,
        icon = icon,
        backgroundColor = backgroundColor,
        showDate
    )

}


@Composable
fun SearchBar(inputText: MutableState<String>, onButtonClick: () -> Unit) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = com.intuit.sdp.R.dimen._18sdp))
            .padding(top = dimensionResource(id = com.intuit.sdp.R.dimen._32sdp))

    ) {
        OutlinedTextField(
            value = inputText.value,
            onValueChange = { inputText.value = it },
            label = { Text(stringResource(R.string.enter_city_name)) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(dimensionResource(id = com.intuit.sdp.R.dimen._6sdp))
        )

        Button(
            onClick = { onButtonClick() },
            modifier = Modifier
                .padding(start = dimensionResource(id = com.intuit.sdp.R.dimen._6sdp))
                .align(Alignment.CenterVertically)
        ) {
            Text(stringResource(id = R.string.search))
        }
    }

}
