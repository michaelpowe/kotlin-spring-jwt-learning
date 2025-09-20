package xyz.powe.kotlinspringjwtlearning.user.application.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.powe.kotlinspringjwtlearning.user.application.dto.RegisterRequest
import xyz.powe.kotlinspringjwtlearning.user.application.dto.UserResponse
import xyz.powe.kotlinspringjwtlearning.user.application.mapper.toUserResponse
import xyz.powe.kotlinspringjwtlearning.user.application.service.UserService

@RestController
@RequestMapping("/api/auth")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody body: RegisterRequest): UserResponse {
        return userService.register(
            email = body.email,
            username = body.username,
            password = body.password
        ).toUserResponse()
    }

}