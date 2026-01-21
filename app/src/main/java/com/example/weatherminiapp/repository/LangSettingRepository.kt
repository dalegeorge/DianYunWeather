package com.example.weatherminiapp.repository

import com.example.weatherminiapp.dao.LanguageDao
import com.example.weatherminiapp.entities.LanguageSettings
import kotlinx.coroutines.flow.Flow

class LangSettingRepository(private val langDao: LanguageDao) {
    suspend fun setLanguage(languageSettings: String)
    {
        langDao.insertLanguage(LanguageSettings(id = 0, languageOption = languageSettings))
    }

    fun getLanguage(): Flow<LanguageSettings?> = langDao.getLanguage()

}