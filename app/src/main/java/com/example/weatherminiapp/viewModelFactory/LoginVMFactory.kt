package com.example.weatherminiapp.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.weatherminiapp.database.BaseDatabase
import com.example.weatherminiapp.repository.UserRepository
import com.example.weatherminiapp.viewModels.DianYunWeatherViewModel
import com.example.weatherminiapp.viewModels.UserLoginViewModel

class LoginVMFactory(private val userRepository: UserRepository): ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(UserLoginViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return UserLoginViewModel(userRepository = userRepository) as T
        }
        throw IllegalArgumentException("user viewmodel class is illegal")
    }
}