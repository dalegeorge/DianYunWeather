package com.example.weatherminiapp.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("langSettings")
data class LanguageSettings(
    @PrimaryKey val id: Int = 0,
    val languageOption: String,

)