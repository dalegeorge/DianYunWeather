package com.example.weatherminiapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home

enum class AppDestinations(
    val label: String,
//    val icon: MenuSource,
) {
    WEATHER("天气"),
//    LOGIN("登录", MenuSource.Vector Icons.Default.AccountBox)
    LOGIN("登录")
}

