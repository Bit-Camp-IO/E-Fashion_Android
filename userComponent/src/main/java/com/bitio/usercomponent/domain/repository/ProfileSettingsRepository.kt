package com.bitio.usercomponent.domain.repository

import com.bitio.usercomponent.domain.model.profile.LocalProfileSettings
import kotlinx.coroutines.flow.Flow

interface ProfileSettingsRepository {
    suspend fun saveProfileSettings(settings: LocalProfileSettings)
    fun getProfileSettings(): Flow<LocalProfileSettings>
}