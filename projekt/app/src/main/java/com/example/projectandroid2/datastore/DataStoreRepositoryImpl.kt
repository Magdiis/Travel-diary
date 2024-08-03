package com.example.projectandroid2.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context)
    : IDataStoreRepository {
    override suspend fun setLoginSuccessful() {
        val preferencesKey = booleanPreferencesKey(DataStoreConstants.LOGIN_SUCCESSFUL)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = false
        }

    }

    override suspend fun getLoginSuccessful(): Boolean {
        return try {
            val preferencesKey = booleanPreferencesKey(DataStoreConstants.LOGIN_SUCCESSFUL)
            val preferences = context.dataStore.data.first()
            if (!preferences.contains(preferencesKey))
                true
            else
                preferences[preferencesKey]!!
        } catch (e: Exception) {
            e.printStackTrace()
            true
        }

    }

    override suspend fun removeLoginSuccessful() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }

    }

    override suspend fun setName(name: String) {
        val preferencesKey = stringPreferencesKey(DataStoreConstants.NAME)
        context.dataStore.edit {
            it[preferencesKey] = name
        }
    }

    override suspend fun getName(): String {
        val preferencesKey = stringPreferencesKey(DataStoreConstants.NAME)
        val name = context.dataStore.data.map {
            it[preferencesKey]
        }
        return name.first().toString()
    }

    override suspend fun setID(id: String) {
        val preferencesKey = stringPreferencesKey(DataStoreConstants.ID)
        context.dataStore.edit {
            it[preferencesKey] = id
        }
    }

    override suspend fun getID(): String {
        val preferencesKey = stringPreferencesKey(DataStoreConstants.ID)
        val id = context.dataStore.data.map {
            it[preferencesKey]
        }
        return id.first().toString()
    }
}