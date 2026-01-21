package com.example.weatherminiapp.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherminiapp.repository.UserRepository
import com.example.weatherminiapp.viewModels.DianYunWeatherViewModel
import com.example.weatherminiapp.viewModels.UserLoginViewModel

class LoginVMFactory(userRepository: UserRepository): ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserLoginViewModel::class.java))
        {
            return super.create(modelClass) as T
        }
        throw IllegalArgumentException("user viewmodel class is illegal")
    }
}