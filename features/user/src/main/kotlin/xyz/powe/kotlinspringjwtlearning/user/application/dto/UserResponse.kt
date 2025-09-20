package xyz.powe.kotlinspringjwtlearning.user.application.dto

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import xyz.powe.kotlinspringjwtlearning.user.domain.model.UserId

@JsonPropertyOrder("id", "email", "username")
data class UserResponse(
    val id: UserId,
    val email: String,
    val username: String,
)
