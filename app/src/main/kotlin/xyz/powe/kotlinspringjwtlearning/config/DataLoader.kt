package xyz.powe.kotlinspringjwtlearning.config

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import xyz.powe.kotlinspringjwtlearning.user.application.service.UserService
import xyz.powe.kotlinspringjwtlearning.user.domain.exception.UserAlreadyExistsException
import xyz.powe.kotlinspringjwtlearning.user.domain.model.UserRole

@Component
class DataLoader(
    private val userService: UserService
) : CommandLineRunner {

    override fun run(vararg args: String) {
        createAdminUser()
        createUser()
    }

    private fun createAdminUser() {
        try {
            // Check if admin user already exists
            val existingAdmin = userService.findByEmail("admin@example.com")
            if (existingAdmin != null) {
                println("Admin user already exists")
                return
            }

            // Create admin user
            userService.registerWithRole(
                email = "admin@example.com",
                username = "admin",
                password = "admin123Admin!@#",
                role = UserRole.ADMIN
            ).also { user ->
                println("✅ Admin user created successfully:")
                println("   Email: ${user.email}")
                println("   Username: ${user.username}")
                println("   Password: ${user.hashedPassword}")
                println("   Role: ${user.role}")
            }


        } catch (e: UserAlreadyExistsException) {
            println("Admin user already exists: ${e.message}")
        } catch (e: Exception) {
            println("Failed to create admin user: ${e.message}")
        }
    }

    private fun createUser() {
        try {
            // Check if user exists.
            val existingUser = userService.findByEmail("test@test.com")
            if (existingUser != null) {
                println("User already exists")
                return
            }

            // Create user
            userService.register(
                email = "test@test.com",
                username = "test",
                password = "testpassw0rd!@#"
            ).also { user ->
                println("✅ User created successfully:")
                println("   Email: ${user.email}")
                println("   Username: ${user.username}")
                println("   Password: ${user.hashedPassword}")
                println("   Role: ${user.role}")
            }


        } catch (e: UserAlreadyExistsException) {
            println("Admin user already exists: ${e.message}")
        } catch (e: Exception) {
            println("Failed to create admin user: ${e.message}")
        }
    }
}