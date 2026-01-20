package com.example.weatherminiapp.dao

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherminiapp.entities.LanguageSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface languageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguage(languageSettings: LanguageSettings)

    @Query("SELECT * FROM langSettings WHERE id = 0 LIMIT 1")
    suspend fun getLanguage(): Flow<LanguageSettings?>

    @Update
    suspend fun updateLanguage(languageSettings: LanguageSettings)

}