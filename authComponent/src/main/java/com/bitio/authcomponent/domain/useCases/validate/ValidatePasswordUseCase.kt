package com.bitio.authcomponent.domain.useCases.validate

class ValidatePasswordUseCase {

    operator fun invoke(password: String): ValidationResult {
        val passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$".toRegex()
        return if (passwordRegex.matches(password)) {
            ValidationResult(
                successful = true,
                error = ValidForm.ValidPassword
            )
        } else {
            ValidationResult(
                successful = false,
                error = ValidForm.UnValidPassword
            )
        }
    }
}