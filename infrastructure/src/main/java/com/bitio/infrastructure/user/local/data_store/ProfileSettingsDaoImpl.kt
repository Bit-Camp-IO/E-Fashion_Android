package com.bitio.infrastructure.user.local.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bitio.usercomponent.data.local.ProfileSettingsDao
import com.bitio.usercomponent.domain.model.profile.LocalProfileSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileSettingsDaoImpl(private val context: Context) : ProfileSettingsDao {
    override suspend fun saveProfileSettings(localProfileSettings: LocalProfileSettings) {
        context.dataStore.edit { settings ->
            settings[DARK_MODE_ENABLED] = localProfileSettings.darkModeEnabled.toString()
            settings[LANGUAGE] = localProfileSettings.language
        }
    }

    override fun getProfileSettings(): Flow<LocalProfileSettings> {
        return context.dataStore.data.map { preferences ->
            LocalProfileSettingsEntity(
                darkModeEnabled = preferences[DARK_MODE_ENABLED]?.toBoolean() ?: false,
                language = preferences[LANGUAGE] ?: ""
            )
        }
    }

    companion object SettingsKeys {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "profile_settings")
        val DARK_MODE_ENABLED = stringPreferencesKey("dark_mode_enabled")
        val LANGUAGE = stringPreferencesKey("language")
    }

}