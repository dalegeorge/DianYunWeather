package com.example.weatherminiapp.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user")
data class Users(
    @PrimaryKey val id: Int = 0,
    val username: String,
    val isLoggedIn: Boolean
)