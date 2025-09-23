package xyz.powe.kotlinspringjwtlearning.auth.infrastructure.entities

import jakarta.persistence.*
import xyz.powe.kotlinspringjwtlearning.user.domain.model.UserId
import java.time.Instant
import java.util.*

@Entity
@Table(name = "refresh_tokens")
class RefreshTokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(nullable = false, unique = true)
    var token: String,

    @Column(nullable = false)
    var userId: UserId,

    @Column(nullable = false)
    var expiryDate: Instant,

    @Column(nullable = false)
    var createdAt: Instant = Instant.now(),

    @Column(nullable = false)
    var isRevoked: Boolean = false
)