package xyz.powe.kotlinspringjwtlearning.auth.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import xyz.powe.kotlinspringjwtlearning.auth.infrastructure.entities.RefreshTokenEntity
import xyz.powe.kotlinspringjwtlearning.user.domain.model.UserId
import java.util.*

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshTokenEntity, UUID> {

    fun findByToken(token: String): RefreshTokenEntity?

    fun findByUserId(userId: UserId): List<RefreshTokenEntity>

    @Modifying
    @Query("DELETE FROM RefreshTokenEntity r WHERE r.userId = :userId")
    fun deleteByUserId(userId: UserId)

    @Modifying
    @Query("DELETE FROM RefreshTokenEntity r WHERE r.expiryDate < CURRENT_TIMESTAMP")
    fun deleteExpiredTokens()
}