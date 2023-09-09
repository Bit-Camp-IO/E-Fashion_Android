package com.bitio.usercomponent.domain.repository

import com.bitio.usercomponent.domain.model.ProfileSettings
import kotlinx.coroutines.flow.Flow

interface ProfileSettingsRepository {
    suspend fun saveProfileSettings(settings: ProfileSettings)
    fun getProfileSettings(): Flow<ProfileSettings>
}