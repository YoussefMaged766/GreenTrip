package com.example.greentrip.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.greentrip.constants.Constants.dataStore
import kotlinx.coroutines.flow.first

class DataStoreRepository(private val dataStore: DataStore<Preferences>) {

     suspend fun getToken(key: String): String? {
        val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
        val preference = dataStore.data.first()
        return preference[dataStoreKey]
    }


}