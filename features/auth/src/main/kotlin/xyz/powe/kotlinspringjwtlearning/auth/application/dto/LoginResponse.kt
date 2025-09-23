package xyz.powe.kotlinspringjwtlearning.auth.application.dto

import xyz.powe.kotlinspringjwtlearning.user.application.dto.UserResponse

data class LoginResponse(
    val token: String,
    val refreshToken: String,
    val user: UserResponse
)