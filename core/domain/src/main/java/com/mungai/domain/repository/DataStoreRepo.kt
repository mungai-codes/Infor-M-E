package com.mungai.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepo {

    fun getTheme(isDarkTheme: Boolean) : Flow<Boolean>

    suspend fun saveTheme(isDarkThemeEnabled: Boolean)
}