package xyz.powe.kotlinspringjwtlearning.user.domain.model

import java.util.UUID

typealias UserId = UUID

data class User(
    val id: UserId,
    val username: String,
    val email: String,
    val hashedPassword: String,
    val role: UserRole,
)

enum class UserRole {
    USER, ADMIN
}