package com.bitio.authcomponent.domain.useCases.validate

class ValidateTermsUseCase {

    operator fun invoke(acceptedTerms: Boolean): ValidationResult {
        return if (acceptedTerms) {
            ValidationResult(
                successful = true,
                error = ValidForm.AcceptTerm
            )
        } else {
            ValidationResult(
                successful = false,
                error = ValidForm.NotAcceptedTerm
            )
        }
    }
}