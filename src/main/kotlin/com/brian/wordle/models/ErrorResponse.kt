package com.brian.wordle.models

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String
)
