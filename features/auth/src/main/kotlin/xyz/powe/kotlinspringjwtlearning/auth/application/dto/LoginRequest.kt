package xyz.powe.kotlinspringjwtlearning.auth.application.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class LoginRequest @JsonCreator constructor(
    @JsonProperty("email")
    @field:Email(message = "Email must be valid")
    @field:NotBlank(message = "Email is required")
    val email: String,

    @JsonProperty("password")
    @field:NotBlank(message = "Password is required")
    val password: String
)