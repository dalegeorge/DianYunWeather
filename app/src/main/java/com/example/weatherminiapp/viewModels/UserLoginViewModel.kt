package com.example.weatherminiapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherminiapp.entities.Users
import com.example.weatherminiapp.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.log


class UserLoginViewModel(
    private val userRepository : UserRepository
    ): ViewModel()
{
    private val _currentUser = MutableStateFlow<Users?>(null)
    val currentUser: StateFlow<Users?> = _currentUser.asStateFlow()

    private val _logginResult = MutableLiveData<Boolean>()
    val logginResult: LiveData<Boolean> = _logginResult

    init {
        viewModelScope.launch {
            _currentUser.value = userRepository.getLogginedUser()
        }
    }
    fun userLogin(username : String, password: String)
    {
        viewModelScope.launch {
            val loggined = userRepository.validateUser(username = username, password = password)
            _logginResult.value = loggined
            userRepository.addUser(username = username, password,true)
            if(loggined)
            {
                _currentUser.value = userRepository.getLogginedUser()
            }
        }
    }

    fun logout(currentName: String) {
        viewModelScope.launch {
            userRepository.logout(currentName)
            _currentUser.value = null
        }
    }
}