package com.example.finalprogect_shift.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

class UserPreferencesStorage @Inject constructor(context: Context){

    private val appContext = context.applicationContext

    val token: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[KEY_TOKEN]
        }


    suspend fun saveToken(token: String) {
        appContext.dataStore.edit { preferences ->
            preferences[KEY_TOKEN] = token
        }
    }

    companion object {
        private val KEY_TOKEN = stringPreferencesKey("key_token")
    }

}