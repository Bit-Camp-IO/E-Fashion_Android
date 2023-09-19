package com.bitio.authcomponent.domain.useCases.validate


class ValidateFullNameUseCase {

    operator fun invoke(fullName: String): ValidationResult {
        return if (fullName.isNotEmpty() && fullName.length >= 8) {
            ValidationResult(
                successful = true,
                error = ValidForm.ValidFullName
            )
        } else {
            ValidationResult(
                successful = false,
                error = ValidForm.UnValidFullName
            )
        }
    }
}