package com.example.weatherminiapp.repository

import androidx.lifecycle.LiveData
import com.example.weatherminiapp.dao.UserDao
import com.example.weatherminiapp.entities.Users
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class UserRepository(private val userDao: UserDao ) {
    fun getUser(): LiveData<Users?> = userDao.getAllUser()

    suspend fun addUser(username: String, isLoggedInState: Boolean)
    {
        userDao.insertSingleUser(Users(id = 0, username = username, isLoggedIn = isLoggedInState))
    }

    suspend fun logout()
    {
        val currentUser = userDao.getAllUser().value
        if(currentUser != null)
        {
            userDao.updateUser(currentUser.copy(isLoggedIn = false))
        }
    }

}