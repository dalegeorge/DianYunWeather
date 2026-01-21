package com.example.weatherminiapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherminiapp.repository.LangSettingRepository
import com.example.weatherminiapp.repository.UserRepository
import kotlinx.coroutines.launch

data class DianYunWeatherUiState(
    val selectedImageUri: String?
)

class DianYunWeatherViewModel(
    private val userRepository : UserRepository,
    private val settingRespotory : LangSettingRepository
) : ViewModel(){

    val currentUser = userRepository.getUser()
    val currentLanguage = settingRespotory.getLanguage()

    fun setCurrentLanguage(nextLang : String)
    {
        viewModelScope.launch { settingRespotory.setLanguage(nextLang) }
    }
}