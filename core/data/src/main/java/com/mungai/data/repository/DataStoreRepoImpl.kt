package com.mungai.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.mungai.domain.repository.DataStoreRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepoImpl @Inject constructor(
    private val datastore: DataStore<Preferences>
) : DataStoreRepo {

    private val themeKey = booleanPreferencesKey("theme")
    override fun getTheme(isDarkTheme: Boolean): Flow<Boolean> {
        return datastore.data.map { preferences ->
            preferences[themeKey] ?: isDarkTheme
        }
    }

    override suspend fun saveTheme(isDarkThemeEnabled: Boolean) {
        datastore.edit { preferences ->
            preferences[themeKey] = isDarkThemeEnabled
        }
    }
}