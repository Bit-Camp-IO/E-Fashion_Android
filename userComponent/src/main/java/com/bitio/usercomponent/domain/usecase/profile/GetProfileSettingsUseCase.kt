package com.bitio.usercomponent.domain.usecase.profile

import com.bitio.usercomponent.domain.model.profile.LocalProfileSettings
import com.bitio.usercomponent.domain.repository.ProfileSettingsRepository
import kotlinx.coroutines.flow.Flow

class GetProfileSettingsUseCase(
    private val profileSettingsRepository: ProfileSettingsRepository
) {
    operator fun invoke(): Flow<LocalProfileSettings> {
        return profileSettingsRepository.getProfileSettings()
    }
}