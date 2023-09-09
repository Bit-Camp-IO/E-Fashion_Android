package com.bitio.usercomponent.data.local

import com.bitio.usercomponent.domain.model.ProfileSettings
import kotlinx.coroutines.flow.Flow

interface ProfileSettingsDao {
    suspend fun saveProfileSettings(settings: ProfileSettings)
    fun getProfileSettings(): Flow<ProfileSettings>
}
