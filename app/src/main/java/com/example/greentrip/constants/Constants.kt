package com.example.greentrip.constants

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore



object Constants {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore("save")
    const val BASEURL = "http://192.168.1.2:8080/"

    const val TOKEN = "token"


}