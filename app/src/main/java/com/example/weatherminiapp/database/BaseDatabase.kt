package com.example.weatherminiapp.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherminiapp.dao.LanguageDao
import com.example.weatherminiapp.dao.UserDao
import com.example.weatherminiapp.entities.LanguageSettings
import com.example.weatherminiapp.entities.Users
import org.intellij.lang.annotations.Language

@Database(
    entities = [Users::class, LanguageSettings::class],
    version = 2,
    exportSchema = false,
)
abstract class BaseDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object {
        @Volatile
        private var INSTANCE: BaseDatabase? = null
        fun getDatabase(context : Context): BaseDatabase
        {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, BaseDatabase::class.java,"weather_db").build()
                INSTANCE = instance
                instance
            }
        }
    }
}