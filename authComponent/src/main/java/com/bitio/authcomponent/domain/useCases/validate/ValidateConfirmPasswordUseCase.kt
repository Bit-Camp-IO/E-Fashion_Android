package com.bitio.authcomponent.domain.useCases.validate

class ValidateConfirmPasswordUseCase {

    operator fun invoke(password: String, confirmPassword: String): ValidationResult {
        return if (password == confirmPassword) {
            ValidationResult(
                successful = true,
                error = ValidForm.ValidConfirmPassword
            )
        } else {
            ValidationResult(
                successful = false,
                error = ValidForm.UnValidConfirmPassword
            )
        }
    }
}