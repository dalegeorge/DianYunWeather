package com.example.weatherminiapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.weatherminiapp.entities.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface userDao {
    @Insert
    suspend fun insertSingleUser(vararg users: Users)

    @Update
    suspend fun updateUser(user: Users)

    @Query("SELECT * FROM user where id = 0 LIMIT 1")
    suspend fun getAllUser() : Flow<Users?>

}