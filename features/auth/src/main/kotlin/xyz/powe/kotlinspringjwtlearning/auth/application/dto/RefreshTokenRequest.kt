package xyz.powe.kotlinspringjwtlearning.auth.application.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class RefreshTokenRequest @JsonCreator constructor(
    @JsonProperty("refreshToken")
    @field:NotBlank(message = "Refresh token is required")
    val refreshToken: String
)