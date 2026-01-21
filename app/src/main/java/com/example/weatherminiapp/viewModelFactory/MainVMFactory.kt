package com.example.weatherminiapp.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherminiapp.entities.LanguageSettings
import com.example.weatherminiapp.repository.LangSettingRepository
import com.example.weatherminiapp.repository.UserRepository
import com.example.weatherminiapp.viewModels.DianYunWeatherViewModel

class MainVMFactory(
    private val languageRepository: LangSettingRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DianYunWeatherViewModel::class.java))
        {
            return super.create(modelClass) as T
        }
        throw IllegalArgumentException("viewmodel class is illegal")
    }
}