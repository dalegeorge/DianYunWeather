package com.example.weatherminiapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.weatherminiapp.entities.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insertSingleUser(vararg users: Users)

    @Update
    suspend fun updateUser(user: Users)

    @Query("SELECT * FROM user where id = 0 LIMIT 1")
    fun getAllUser() : LiveData<Users?>
    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    suspend fun findUser(username: String, password: String): Users?

    @Query("SELECT * FROM user WHERE isLoggedIn = 1 LIMIT 1")
    suspend fun getLogginedUser(): Users?


}