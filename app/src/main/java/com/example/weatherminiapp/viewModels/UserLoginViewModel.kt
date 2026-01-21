package com.example.weatherminiapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherminiapp.repository.UserRepository
import kotlinx.coroutines.launch


class UserLoginViewModel(
    private val userRepository : UserRepository

    ): ViewModel()
{
    fun userLogin(username : String)
    {
        viewModelScope.launch {
            userRepository.addUser(username = username,true)
        }
    }

    fun getUser() = userRepository.getUser()
}