package com.bitio.infrastructure.auth.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bitio.authcomponent.data.local.AuthDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthDaoImpl(private val context: Context) : AuthDao {

    override fun getRefreshToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[tokenKey]
        }
    }

    override fun getAccessTokenStream(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[tokenKey]
        }
    }

    override suspend fun updateRefreshToken(token: String) {
        context.dataStore.edit { it[tokenKey] = token }
    }

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        val tokenKey = stringPreferencesKey("refresh token")
    }

}