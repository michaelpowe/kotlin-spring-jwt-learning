package xyz.powe.kotlinspringjwtlearning.auth.application.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import xyz.powe.kotlinspringjwtlearning.user.domain.model.User
import javax.crypto.SecretKey
import java.util.*

@Service
class JwtService(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.expiration}") private val expiration: Long,
) {
    private val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())

    fun generateToken(user: User): String {
        return Jwts.builder()
            .subject(user.email)
            .claim("userId", user.id)
            .claim("role", user.role.name)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(key)
            .compact()
    }

    fun extractUsername(token: String): String {
        return extractClaim(token) { claims -> claims.subject }
    }

    fun extractUserId(token: String): String {
        return extractClaim(token) { claims -> claims["userId"] as String }
    }

    fun extractRole(token: String): String {
        return extractClaim(token) { claims -> claims["role"] as String }
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
    }

    fun isTokenValid(token: String, user: User): Boolean {
        val username = extractUsername(token)
        return username == user.email && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token) { claims -> claims.expiration }
    }
}