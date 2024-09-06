package com.example.myapplication

import androidx.compose.runtime.remember
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DKDataViewModel (val ds: DataStore<Preferences>) : ViewModel() {
//    val ds1 = remember { DataStore<Preferences> }
    fun saveEntry(entries: List<Entry>, ENTRIES_KEY: Preferences.Key<String>) {

        viewModelScope.launch {
            ds.edit { settings ->

                var jsonString = Json.encodeToString(entries)
                settings[ENTRIES_KEY] = jsonString;
            }
        }
    }

    fun loadEntry(ENTRIES_KEY: Preferences.Key<String>): Flow<List<Entry>?> {
        return ds.data.map { preferences ->
            val jsonString = preferences[ENTRIES_KEY]
            jsonString?.let {
                try {
                    Json.decodeFromString<List<Entry>>(it)
                } catch (e: Exception) {
                    null // Handle parsing error
                }
            }
        }
    }
}