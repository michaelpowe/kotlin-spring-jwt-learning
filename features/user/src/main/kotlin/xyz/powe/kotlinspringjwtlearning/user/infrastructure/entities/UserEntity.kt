package xyz.powe.kotlinspringjwtlearning.user.infrastructure.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import xyz.powe.kotlinspringjwtlearning.user.domain.model.UserId
import xyz.powe.kotlinspringjwtlearning.user.domain.model.UserRole

@Entity
@Table(
    name = "users",
    indexes = [
        Index(name = "idx_user_email", columnList = "email"),
        Index(name = "idx_user_username", columnList = "username"),
    ]
)
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UserId? = null,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false, unique = true)
    var username: String,

    @Column(nullable = false)
    var hashedPassword: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: UserRole = UserRole.USER,
)