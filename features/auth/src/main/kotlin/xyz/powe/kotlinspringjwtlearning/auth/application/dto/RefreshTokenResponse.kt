package xyz.powe.kotlinspringjwtlearning.auth.application.dto

data class RefreshTokenResponse(
    val token: String,
    val refreshToken: String
)