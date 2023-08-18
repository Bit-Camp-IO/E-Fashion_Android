package com.bitio.infrastructure.auth.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bitio.authcomponent.data.local.AuthDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch



class AuthDaoImpl(private val context: Context) : AuthDao {

    override suspend fun getRefreshToken(): String {
        var token: String? = null
        val scope = CoroutineScope(Dispatchers.IO)
        val job = scope.launch {
            context.dataStore.data.collectLatest {
                token = it[tokenKey] ?: ""
                scope.cancel()
            }
        }
        job.join()
        return token!!
    }

    override suspend fun updateRefreshToken(token: String) {
        context.dataStore.edit { it[tokenKey] = token }
    }
    companion object{
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        val tokenKey = stringPreferencesKey("refresh token")
    }

}