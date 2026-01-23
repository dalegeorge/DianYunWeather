package com.example.weatherminiapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.ViewModel
import com.example.weatherminiapp.MenuSource
import com.example.weatherminiapp.R
import com.example.weatherminiapp.Temperature
import com.example.weatherminiapp.ui.theme.WeatherMiniAppTheme
import com.example.weatherminiapp.viewModels.WeatherViewModel

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun WeatherScreen(cityCode: String,
                  weatherVM: WeatherViewModel
){
    val weatherList by weatherVM.weatherList.collectAsStateWithLifecycle()
    val loading by weatherVM.loading.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = cityCode) {
        weatherVM.loadWeather(cityAdcode = cityCode)
    }

    Box(Modifier.fillMaxSize(1f), contentAlignment = Alignment.Center) {
        Spacer(Modifier.height(65.dp).fillMaxWidth(1f))

        if(loading)
            {
                CircularProgressIndicator(modifier = Modifier.padding(vertical = 30.dp))
            } else if(weatherList != null && weatherList!!.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(15.dp)
                ) {

                    items(weatherList!!) {
                        tempData -> weatherCard(dayTemp = tempData)
                    }
                }
            }
    }
}

@Composable
fun getWeatherImage(weatherDescip: String, ifDaytime: Boolean): MenuSource.Drawable
{
    return when {
        weatherDescip.contains("晴") ->{
          if(ifDaytime)  MenuSource.Drawable(R.drawable.sunny_24px) else MenuSource.Drawable(R.drawable.partly_cloudy_day_24px)
        }

        else -> {MenuSource.Drawable(R.drawable.sunny_24px)}
    }
}

@Composable
fun weatherCard(dayTemp: Temperature)
{
    Spacer(Modifier.height(55.dp).fillMaxWidth(1f))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp), // 轻微的水平边距
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        "周${dayTemp.week}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                // 显示温度范围
                Text(
                    text = "${dayTemp.daytemp}°C/ ${dayTemp.nighttemp}°C",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
}
@Preview(showBackground = true)
@Composable
fun weatherPreview()
{
    WeatherMiniAppTheme() {
        val sampleDayData = Temperature(
            week = "5",
            dayweather = "晴",
            daytemp = "22",
            nighttemp = "12"
        )
        WeatherScreen("310000", WeatherViewModel())
    }
}

