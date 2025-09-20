package xyz.powe.kotlinspringjwtlearning.user.application.mapper

import xyz.powe.kotlinspringjwtlearning.user.application.dto.UserResponse
import xyz.powe.kotlinspringjwtlearning.user.domain.model.User

fun User.toUserResponse(): UserResponse {
    return UserResponse(
        id = id,
        email = email,
        username = username,
    )
}