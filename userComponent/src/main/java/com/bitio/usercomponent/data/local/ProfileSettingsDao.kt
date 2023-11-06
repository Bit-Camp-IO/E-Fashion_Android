package com.bitio.usercomponent.data.local

import com.bitio.usercomponent.domain.model.profile.LocalProfileSettings
import kotlinx.coroutines.flow.Flow

interface ProfileSettingsDao {
    suspend fun saveProfileSettings(settings: LocalProfileSettings)
    fun getProfileSettings(): Flow<LocalProfileSettings>
}
