package xyz.powe.kotlinspringjwtlearning.user.application.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import xyz.powe.kotlinspringjwtlearning.user.domain.exception.UserAlreadyExistsException
import xyz.powe.kotlinspringjwtlearning.user.domain.model.User
import xyz.powe.kotlinspringjwtlearning.user.infrastructure.entities.UserEntity
import xyz.powe.kotlinspringjwtlearning.user.infrastructure.mapper.toUser
import xyz.powe.kotlinspringjwtlearning.user.infrastructure.repository.UserJpaRepository


@Service
class UserService(
    private val userRepository: UserJpaRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun register(email: String, username: String, password: String): User {
        val user = userRepository.findByEmailOrUsername(
            email = email.trim(),
            username = username.trim()
        )
        if (user != null) {
            throw UserAlreadyExistsException()
        }

        val savedUser = userRepository.save(
            UserEntity(
                email = email.trim(),
                username = username.trim(),
                hashedPassword = passwordEncoder.encode(password)!!
            )
        ).toUser()
        return savedUser
    }
}