package com.bitio.infrastructure.user.local.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bitio.usercomponent.data.local.ProfileSettingsDao
import com.bitio.usercomponent.domain.model.ProfileSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileSettingsDaoImpl(context: Context) : ProfileSettingsDao {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "profile_settings")
    private val dataStore = context.dataStore

    override suspend fun saveProfileSettings(profileSettings: ProfileSettings) {
        dataStore.edit { settings ->
            settings[DARK_MODE_ENABLED] = profileSettings.darkModeEnabled.toString()
            settings[LANGUAGE] = profileSettings.language
        }
    }

    override fun getProfileSettings(): Flow<ProfileSettings> {
        return dataStore.data.map { preferences ->
            ProfileSettingsEntity(
                darkModeEnabled = preferences[DARK_MODE_ENABLED]?.toBoolean() ?: false,
                language = preferences[LANGUAGE] ?: ""
            )
        }
    }

    private companion object SettingsKeys {
        val DARK_MODE_ENABLED = stringPreferencesKey("dark_mode_enabled")
        val LANGUAGE = stringPreferencesKey("language")
    }

}