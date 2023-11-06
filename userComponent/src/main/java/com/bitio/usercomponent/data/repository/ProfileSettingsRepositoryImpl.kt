package com.bitio.usercomponent.data.repository

import com.bitio.usercomponent.data.local.ProfileSettingsDao
import com.bitio.usercomponent.domain.model.profile.LocalProfileSettings
import com.bitio.usercomponent.domain.repository.ProfileSettingsRepository
import kotlinx.coroutines.flow.Flow

class ProfileSettingsRepositoryImpl(
    private val profileSettingsDao: ProfileSettingsDao
) : ProfileSettingsRepository {
    override suspend fun saveProfileSettings(settings: LocalProfileSettings) {
        profileSettingsDao.saveProfileSettings(settings)
    }

    override fun getProfileSettings(): Flow<LocalProfileSettings> {
        return profileSettingsDao.getProfileSettings()
    }
}