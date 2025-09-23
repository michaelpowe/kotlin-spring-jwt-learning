package xyz.powe.kotlinspringjwtlearning.auth.infrastructure.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import xyz.powe.kotlinspringjwtlearning.auth.application.service.JwtService
import xyz.powe.kotlinspringjwtlearning.user.application.service.UserService

@Component
class JwtAuthFilter(
    private val jwtService: JwtService,
    private val userService: UserService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 1. Extract Authorization header
        val authHeader = request.getHeader("Authorization")

        // 2. Check if header exists and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)  // Continue without authentication
            return
        }

        // 3. Extract the token (remove "Bearer " prefix)
        val token = authHeader.substring(7)

        try {
            // 4. Extract username from token
            val username = jwtService.extractUsername(token)

            // 5. Check if user is not already authenticated
            if (SecurityContextHolder.getContext().authentication == null) {

                // 6. Load user from database (already converted to domain model)
                val user = userService.findByEmail(username)

                if (user != null && jwtService.isTokenValid(token, user)) {
                    // 7. Create authorities from user role
                    val authorities = listOf(SimpleGrantedAuthority("ROLE_${user.role.name}"))

                    // 8. Create authentication token
                    val authToken = UsernamePasswordAuthenticationToken(
                        user.email,        // principal (who they are)
                        null,              // credentials (password - not needed for JWT)
                        authorities        // authorities (what they can do)
                    )

                    // 9. Set additional details
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                    // 10. Set authentication in Spring Security context
                    SecurityContextHolder.getContext().authentication = authToken
                }
            }
        } catch (e: Exception) {
            // Token is invalid - do nothing, request will be unauthenticated
        }

        // 11. Continue the filter chain
        filterChain.doFilter(request, response)
    }
}