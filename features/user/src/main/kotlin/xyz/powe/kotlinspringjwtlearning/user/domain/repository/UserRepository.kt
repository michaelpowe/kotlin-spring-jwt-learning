package xyz.powe.kotlinspringjwtlearning.user.domain.repository

import xyz.powe.kotlinspringjwtlearning.user.domain.model.User

interface UserRepository {
    fun findByEmail(email: String): User?
    fun findByEmailOrUsername(email: String, username: String): User?
}