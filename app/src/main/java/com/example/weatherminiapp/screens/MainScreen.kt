package com.example.weatherminiapp.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.weatherminiapp.R
import com.example.weatherminiapp.WeatherRespones
import com.example.weatherminiapp.ui.theme.WeatherMiniAppTheme
import com.example.weatherminiapp.viewModels.DianYunWeatherViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherminiapp.AppDestinations
import com.example.weatherminiapp.Forecast
import com.example.weatherminiapp.MenuSource
import com.example.weatherminiapp.cityOptions.CityMenu
import com.example.weatherminiapp.viewModels.UserLoginViewModel
import com.example.weatherminiapp.viewModels.WeatherViewModel
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

@OptIn(ExperimentalMaterial3Api::class)
@PreviewScreenSizes
@Composable
fun WeatherMiniAppApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.WEATHER) }
    var currentCityCode by rememberSaveable { mutableStateOf("310000") }
    var isUserLogin by rememberSaveable { mutableStateOf(true) }
    val weatherViewModel: WeatherViewModel = viewModel()
//    val loginViewModel: UserLoginViewModel = viewModel()

    fun switchCity(newCity: String) {
        currentCityCode = newCity
        weatherViewModel.loadWeather(currentCityCode)
    }
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach { destinations ->
                item(
                    icon = {
//                        Icon(painter = painterResource(R.drawable.sunny_24px),
//                            contentDescription = null)
                        when (val iconSource = destinations.icon)
                        {
                            is MenuSource.Vector -> {
                                Icon(
                                    imageVector = iconSource.imageVector,
                                    contentDescription = destinations.label
                                )

                            }
                            is MenuSource.Drawable -> {
                                Icon(
                                    painter = painterResource(iconSource.resId),
                                    contentDescription = destinations.label
                                )
                            }
                        }
                    },
                    label = { Text(destinations.label) },
                    selected = currentDestination == destinations,
                    onClick = { currentDestination = destinations }
                )
            }
        }
    ) {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

        Scaffold(
            modifier = Modifier.fillMaxSize(1f).nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(
                            "天气预报", maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
//                    navigationIcon = {
//                        IconButton(onClick = {
//                            Log.d("Login page","navigating to Login screen")
//                            currentDestination = AppDestinations.LOGIN
//                        })
//                        {
//                            Icon(
//                                imageVector = Icons.Filled.AccountBox,
//                                contentDescription = "menu icon"
//                            )
//                        }
//                    },
                    actions = {
                        if(isUserLogin) {
                            CityMenu(
                                currentCity = { newCityCode -> switchCity(newCityCode) },
                                isUserLogin
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Surface(
                modifier = Modifier.fillMaxSize(1f)
                    .padding(innerPadding),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                LaunchedEffect(Unit) {
                    weatherViewModel.loadWeather(currentCityCode)
                }
                when (currentDestination) {
                    AppDestinations.WEATHER -> WeatherScreen(currentCityCode, weatherViewModel)
                    AppDestinations.LOGIN -> {
                        val intent = Intent(LocalContext.current, LoginActivity::class.java)
                        LocalContext.current.startActivity(intent)
                    }
                }
            }
        }
    }
}


@Composable
fun TestWeather()
{
    val lifecycleScope = rememberCoroutineScope()
    Button(onClick = {
        println()
        lifecycleScope.launch {
            val forecast = WeatherRespones.getEveryWeekForecast("440300")
            Log.d("weather info", "Result: ${forecast}")
        }
    },Modifier.height(70.dp).width(150.dp)) {

        Text("Show Weather")
    }
}

@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    WeatherMiniAppTheme {
        WeatherMiniAppApp()
    }
}