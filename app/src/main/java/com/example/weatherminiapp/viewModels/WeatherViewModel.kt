package com.example.weatherminiapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherminiapp.Temperature
import com.example.weatherminiapp.WeatherRespones
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(): ViewModel() {
    private val _weatherList = MutableStateFlow<List<Temperature>?>(null)
    val weatherList: StateFlow<List<Temperature>?> = _weatherList

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun loadWeather(cityAdcode: String) {
        viewModelScope.launch {
            _loading.value = true
            _weatherList.value = WeatherRespones.getEveryWeekForecast(cityAdcode)
            _loading.value = false
        }
    }
}