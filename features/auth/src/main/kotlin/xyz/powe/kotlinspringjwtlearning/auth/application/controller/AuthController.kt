package xyz.powe.kotlinspringjwtlearning.auth.application.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.security.core.Authentication
import xyz.powe.kotlinspringjwtlearning.auth.application.dto.LoginRequest
import xyz.powe.kotlinspringjwtlearning.auth.application.dto.LoginResponse
import xyz.powe.kotlinspringjwtlearning.auth.application.dto.RefreshTokenRequest
import xyz.powe.kotlinspringjwtlearning.auth.application.dto.RefreshTokenResponse
import xyz.powe.kotlinspringjwtlearning.auth.application.service.JwtService
import xyz.powe.kotlinspringjwtlearning.auth.application.service.RefreshTokenService
import xyz.powe.kotlinspringjwtlearning.user.application.dto.RegisterRequest
import xyz.powe.kotlinspringjwtlearning.user.application.dto.UserResponse
import xyz.powe.kotlinspringjwtlearning.user.application.mapper.toUserResponse
import xyz.powe.kotlinspringjwtlearning.user.application.service.UserService
import xyz.powe.kotlinspringjwtlearning.user.domain.model.UserRole

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userService: UserService,
    private val jwtService: JwtService,
    private val refreshTokenService: RefreshTokenService,
    private val authenticationManager: AuthenticationManager
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody body: RegisterRequest): ResponseEntity<UserResponse> {
        val user = userService.register(
            email = body.email,
            username = body.username,
            password = body.password
        )
        return ResponseEntity.ok(user.toUserResponse())
    }

    @PostMapping("/register-admin")
    @PreAuthorize("hasRole('ADMIN')")
    fun registerAdmin(@Valid @RequestBody body: RegisterRequest): ResponseEntity<UserResponse> {
        val user = userService.registerWithRole(
            email = body.email,
            username = body.username,
            password = body.password,
            role = UserRole.ADMIN
        )
        return ResponseEntity.ok(user.toUserResponse())
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody body: LoginRequest): ResponseEntity<LoginResponse> {
        // Authenticate user
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(body.email, body.password)
        )

        // If authentication succeeds, find user and generate token
        val user = userService.findByEmail(body.email)
            ?: return ResponseEntity.badRequest().build()

        val token = jwtService.generateToken(user)
        val refreshToken = refreshTokenService.createRefreshToken(user.id)

        return ResponseEntity.ok(LoginResponse(
            token = token,
            refreshToken = refreshToken.token,
            user = user.toUserResponse()
        ))
    }

    @PostMapping("/refresh")
    fun refreshToken(@Valid @RequestBody request: RefreshTokenRequest): ResponseEntity<RefreshTokenResponse> {
        return try {
            // Find and verify refresh token
            val refreshTokenEntity = refreshTokenService.findByToken(request.refreshToken)
                ?: return ResponseEntity.badRequest().build()

            // Check if token is expired
            refreshTokenService.verifyExpiration(refreshTokenEntity)

            // Find user and generate new tokens
            val user = userService.findById(refreshTokenEntity.userId)
                ?: return ResponseEntity.badRequest().build()

            val newAccessToken = jwtService.generateToken(user)
            val newRefreshToken = refreshTokenService.createRefreshToken(user.id)

            ResponseEntity.ok(RefreshTokenResponse(
                token = newAccessToken,
                refreshToken = newRefreshToken.token
            ))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/me")
    fun getCurrentUser(authentication: Authentication): ResponseEntity<UserResponse> {
        // Get the email from the JWT token (set by JwtAuthFilter)
        val email = authentication.name

        // Find the user in the database
        val user = userService.findByEmail(email)
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(user.toUserResponse())
    }

}