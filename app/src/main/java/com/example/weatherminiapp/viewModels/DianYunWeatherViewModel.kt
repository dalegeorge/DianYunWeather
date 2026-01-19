package com.example.weatherminiapp.viewModels

import androidx.lifecycle.ViewModel
import com.example.weatherminiapp.repository.SettingRespoitory
import com.example.weatherminiapp.repository.UserRepository

data class DianYunWeatherUiState(
    val selectedImageUri: String?
)

class DianYunWeatherViewModel(
    private val userRepository : UserRepository,
    private val settingRespotory : SettingRespoitory
) : ViewModel(){

    val currentUser = userRepository.getUser()
}