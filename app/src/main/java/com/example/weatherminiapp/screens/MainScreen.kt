package com.example.weatherminiapp.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.weatherminiapp.R
import com.example.weatherminiapp.WeatherRespones
import com.example.weatherminiapp.ui.theme.WeatherMiniAppTheme
import com.example.weatherminiapp.viewModels.DianYunWeatherViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherminiapp.Forecast
import kotlinx.coroutines.launch

class MainMenu : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherMiniAppTheme {
                WeatherMiniAppApp()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun WeatherMiniAppApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(1f)) { innerPadding ->
            Column(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(1f)

            ) {
                Spacer(Modifier.height(65.dp).fillMaxWidth(1f))
                ShowBeijingWeather()

                Row(modifier = Modifier.align(Alignment.End).height(50.dp).width(50.dp)) {
                    DropdownMenu(expanded = false, onDismissRequest = {

                    },modifier = Modifier.height(50.dp).width(150.dp)
                        ) {

                    }
                }

            }
//            Greeting(
//                name = "Android",
//                modifier = Modifier.padding(innerPadding)
//            )
            Spacer(Modifier.height(65.dp).fillMaxWidth(1f))
        }

    }
}

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("天气", Icons.Default.Home),
    FAVORITES("外卖菜单", Icons.Default.Menu),
//    PROFILE("Profile", Icons.Default.Call),
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
@Composable
fun ShowBeijingWeather()
{
    val lifecycleScope = rememberCoroutineScope()
    Button(onClick = {
        println()
        lifecycleScope.launch {
            val forecast = WeatherRespones.getEveryWeekForecast("440300")
            Log.d("weather info", "Result: ${forecast?.city}")
        }
    },Modifier.height(70.dp).width(150.dp)) {

        Text("Show Weather")
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherMiniAppTheme {
        WeatherMiniAppApp()
    }
}