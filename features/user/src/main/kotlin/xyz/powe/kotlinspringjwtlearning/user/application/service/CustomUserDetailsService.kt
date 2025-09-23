package xyz.powe.kotlinspringjwtlearning.user.application.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import xyz.powe.kotlinspringjwtlearning.user.infrastructure.repository.UserJpaRepository

@Service
class CustomUserDetailsService(
    private val userRepository: UserJpaRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity = userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User not found: $username")

        return org.springframework.security.core.userdetails.User.builder()
            .username(userEntity.email)
            .password(userEntity.hashedPassword)
            .authorities("ROLE_${userEntity.role.name}")
            .build()
    }
}