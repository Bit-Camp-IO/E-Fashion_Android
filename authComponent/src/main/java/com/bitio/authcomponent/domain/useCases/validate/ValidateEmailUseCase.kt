package com.bitio.authcomponent.domain.useCases.validate


class ValidateEmailUseCase {

    operator fun invoke(email: String): ValidationResult {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)\\.com$".toRegex()
        return if (emailRegex.matches(email)) {
            ValidationResult(
                successful = true,
                error = ValidForm.ValidEmail
            )
        } else {
            ValidationResult(
                successful = false,
                error = ValidForm.UnValidEmail
            )
        }
    }
}