package xyz.powe.kotlinspringjwtlearning.user.infrastructure.mapper

import xyz.powe.kotlinspringjwtlearning.user.domain.model.User
import xyz.powe.kotlinspringjwtlearning.user.infrastructure.entities.UserEntity

fun UserEntity.toUser(): User {
    return User(
        id = id!!,
        username = username,
        email = email,
        hashedPassword = hashedPassword,
        role = role,
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        username = username,
        email = email,
        hashedPassword = hashedPassword,
        role = role,
    )
}