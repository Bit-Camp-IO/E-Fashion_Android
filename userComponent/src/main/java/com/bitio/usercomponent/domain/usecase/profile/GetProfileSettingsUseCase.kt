package com.bitio.usercomponent.domain.usecase.profile

import com.bitio.usercomponent.domain.model.ProfileSettings
import com.bitio.usercomponent.domain.repository.ProfileSettingsRepository
import kotlinx.coroutines.flow.Flow

class GetProfileSettingsUseCase(
    private val profileSettingsRepository: ProfileSettingsRepository
) {
    operator fun invoke(): Flow<ProfileSettings> {
        return profileSettingsRepository.getProfileSettings()
    }
}