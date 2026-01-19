package com.example.weatherminiapp

data class WeatherData(
    val status: String,
    val count: String,
    val forecast: List<Forecast>
)

data class Forecast(
    val city: String,
    val cityCodes: String,
    val casts: List<temperature>
)

data class temperature(
    val tempFloat: String,
    val weatherDescrp: String
)