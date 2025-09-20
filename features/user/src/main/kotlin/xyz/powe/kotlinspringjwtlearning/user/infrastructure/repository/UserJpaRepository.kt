package xyz.powe.kotlinspringjwtlearning.user.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import xyz.powe.kotlinspringjwtlearning.user.domain.model.UserId
import xyz.powe.kotlinspringjwtlearning.user.infrastructure.entities.UserEntity

interface UserJpaRepository: JpaRepository<UserEntity, UserId> {
    fun findByEmail(email: String): UserEntity?
    fun findByEmailOrUsername(email: String, username: String): UserEntity?
}