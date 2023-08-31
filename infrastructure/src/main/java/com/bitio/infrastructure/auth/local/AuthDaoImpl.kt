package com.bitio.infrastructure.auth.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bitio.authcomponent.data.local.AuthDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class AuthDaoImpl(private val context: Context) : AuthDao {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val tokenFlow = MutableStateFlow("Initial Value")

    init {
        observeTokenFromDataStore()
    }

    override suspend fun getRefreshToken(): String {
        var token: String? = null
        val scope = CoroutineScope(Dispatchers.IO)
        val job = scope.launch {
            context.dataStore.data.collectLatest {
                token = it[tokenKey]
                Log.d("vvv",token.toString())
                scope.cancel()

            }
        }
        job.join()
        return token!!
    }

    override fun getAccessTokenStream(): Flow<String> {

        return tokenFlow

    }

    override suspend fun updateRefreshToken(token: String) {
        context.dataStore.edit { it[tokenKey] = token }
    }

    private fun observeTokenFromDataStore() {
        coroutineScope.launch {
            context.dataStore.data.collect {
                val token = it[tokenKey]
                if (token!=null)
                    tokenFlow.emit(token)
            }
        }

    }

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        val tokenKey = stringPreferencesKey("refresh token")
    }

}