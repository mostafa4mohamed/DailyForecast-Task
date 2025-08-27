package com.my_app.daily_forecast.ui.screens.home_screen.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.my_app.daily_forecast.R

@Composable
fun ErrorContent(
    imageRes: Int = R.drawable.ic_baseline_warning_24,
    title: String = stringResource(R.string.sorry_error),
    message: String? = stringResource(R.string.sorry_error),
    retryText: String = stringResource(R.string.retry),
    inputText: MutableState<String>,
    onRetryClick: () -> Unit
) {

    val backgroundColor = MaterialTheme.colorScheme.background

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

        SearchBar(inputText, onRetryClick)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = dimensionResource(id = com.intuit.sdp.R.dimen._4sdp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(dimensionResource(id = com.intuit.sdp.R.dimen._70sdp))
            )

            Text(
                text = title,
                fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._24ssp).value.sp,
                color = colorResource(id = R.color.teal_200),
                modifier = Modifier
                    .padding(top = dimensionResource(id = com.intuit.sdp.R.dimen._8sdp))
            )

            Text(
                text = message ?: "",
                fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._18ssp).value.sp,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = com.intuit.sdp.R.dimen._4sdp))
            )

            Surface(
                shape = RoundedCornerShape(com.intuit.ssp.R.dimen._18ssp.dp),
                border = BorderStroke(2.dp, colorResource(id = R.color.teal_200)),
                color = colorResource(id = R.color.white),
                modifier = Modifier.padding(top = dimensionResource(id = com.intuit.sdp.R.dimen._12sdp))

            ) {
                Text(
                    text = retryText,
                    fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._18ssp).value.sp,
                    color = colorResource(id = R.color.teal_200),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(
                            horizontal = dimensionResource(id = com.intuit.sdp.R.dimen._12sdp),
                            vertical = dimensionResource(id = com.intuit.sdp.R.dimen._4sdp)
                        )
                        .clickable { onRetryClick() }
                )
            }
        }


    }


}