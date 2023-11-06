package com.bitio.usercomponent.domain.usecase.profile

import com.bitio.usercomponent.domain.model.profile.LocalProfileSettings
import com.bitio.usercomponent.domain.repository.ProfileSettingsRepository

class SaveProfileSettingsUseCase(
    private val profileSettingsRepository: ProfileSettingsRepository
) {
    suspend operator fun invoke(settings: LocalProfileSettings){
        profileSettingsRepository.saveProfileSettings(settings)
    }
}