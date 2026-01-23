package com.example.weatherminiapp

import androidx.compose.ui.graphics.vector.ImageVector

sealed class MenuSource {
    data class Vector(val imageVector: ImageVector): MenuSource()
    data class Drawable(val resId: Int): MenuSource()
}