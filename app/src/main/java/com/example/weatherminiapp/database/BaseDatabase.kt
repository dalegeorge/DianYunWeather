package com.example.weatherminiapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherminiapp.dao.LanguageDao
import com.example.weatherminiapp.dao.UserDao
import com.example.weatherminiapp.entities.LanguageSettings
import com.example.weatherminiapp.entities.Users

@Database(
    entities = [Users::class, LanguageSettings::class],
    version = 1,
    exportSchema = false
)
abstract class BaseDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun languageDao(): LanguageDao

    companion object {
        @Volatile
        private var INSTANCE: BaseDatabase? = null

        fun getInstance(context : Context): BaseDatabase
        {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context, BaseDatabase::class.java,"weather_db").build()
                INSTANCE = instance
                instance
            }
        }
    }
}