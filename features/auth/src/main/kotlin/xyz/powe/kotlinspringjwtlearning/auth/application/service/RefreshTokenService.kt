package xyz.powe.kotlinspringjwtlearning.auth.application.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.powe.kotlinspringjwtlearning.auth.infrastructure.entities.RefreshTokenEntity
import xyz.powe.kotlinspringjwtlearning.auth.infrastructure.repository.RefreshTokenRepository
import xyz.powe.kotlinspringjwtlearning.user.domain.model.UserId
import java.time.Instant
import java.util.*

@Service
@Transactional
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
    @Value("\${jwt.refresh-expiration:604800000}") // 7 days default
    private val refreshTokenDurationMs: Long
) {

    fun createRefreshToken(userId: UserId): RefreshTokenEntity {
        // Delete any existing refresh tokens for this user (optional - single device login)
        refreshTokenRepository.deleteByUserId(userId)

        val refreshToken = RefreshTokenEntity(
            token = UUID.randomUUID().toString(),
            userId = userId,
            expiryDate = Instant.now().plusMillis(refreshTokenDurationMs)
        )

        return refreshTokenRepository.save(refreshToken)
    }

    fun findByToken(token: String): RefreshTokenEntity? {
        return refreshTokenRepository.findByToken(token)
    }

    fun verifyExpiration(token: RefreshTokenEntity): RefreshTokenEntity {
        if (token.expiryDate < Instant.now()) {
            refreshTokenRepository.delete(token)
            throw RuntimeException("Refresh token was expired. Please make a new signin request")
        }
        return token
    }

    fun deleteByUserId(userId: UserId) {
        refreshTokenRepository.deleteByUserId(userId)
    }

    @Transactional
    fun cleanupExpiredTokens() {
        refreshTokenRepository.deleteExpiredTokens()
    }
}