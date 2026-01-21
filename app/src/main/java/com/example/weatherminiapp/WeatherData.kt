package com.example.weatherminiapp

data class WeatherData(
    val status: String,
    val count: String,
    val info: String,
    val infocode: String,
    val forecasts: List<Forecast>?
)

data class Forecast(
    val city: String,
    val adcode: String,
    val casts: List<Temperature>
)

data class Temperature(
    val week: String,
    val dayweather: String,
    val daytemp: String,
    val nighttemp: String
)