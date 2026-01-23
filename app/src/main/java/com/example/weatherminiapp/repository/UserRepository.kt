package com.example.weatherminiapp.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.weatherminiapp.dao.UserDao
import com.example.weatherminiapp.entities.Users
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

open class UserRepository(private val userDao: UserDao ) {
    fun getUser(): LiveData<Users?> = userDao.getAllUser()
    suspend fun getLogginedUser(): Users?{
        return userDao.getLogginedUser()
    }
    suspend fun validateUser(username: String, password: String): Boolean {
        val user = userDao.findUser(username, password)
        return if (user != null) {

            val updatedUser = user.copy(isLoggedIn = true)
            userDao.updateUser(updatedUser)
            true
        } else {
            false
        }
    }
    @WorkerThread
    suspend fun addUser(username: String, password: String,isLoggedInState: Boolean)
    {
        userDao.insertSingleUser(Users(id = 0, username = username, password = password,isLoggedIn = isLoggedInState))
    }

    open suspend fun logout(currentUserName: String)
    {
        val currentUser = userDao.findUser(currentUserName,"")

        currentUser?.let {
            val loggedOutUser = it.copy(isLoggedIn = false)
            userDao.updateUser(loggedOutUser)
        }

    }

}