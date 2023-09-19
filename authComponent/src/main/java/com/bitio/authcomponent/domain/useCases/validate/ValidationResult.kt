package com.bitio.authcomponent.domain.useCases.validate

data class ValidationResult(
    val successful: Boolean,
    val error: ValidForm
)