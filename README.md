# Kotlin Spring JWT Learning Project

A modular Spring Boot application demonstrating JWT authentication with refresh tokens and role-based authorization using Spring Security Authentication Manager.

## Features

✅ **JWT Authentication**
- Access tokens (15 minutes) + Refresh tokens (7 days)
- HMAC-SHA256 signature algorithm
- Stateless authentication

✅ **Role-Based Authorization**
- USER and ADMIN roles
- Method-level security with `@PreAuthorize`
- Protected admin endpoints

✅ **Authentication Endpoints**
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `POST /api/auth/register-admin` - Admin creation (admin only)
- `POST /api/auth/refresh` - Token refresh
- `GET /api/auth/me` - Current user profile

✅ **Security Features**
- Password hashing with BCrypt
- Custom JWT authentication filter
- Proper error handling with JSON responses
- Single-session security (old tokens invalidated)

## Tech Stack
- **Kotlin 2.2.20** with Java 21
- **Spring Boot 4.0.0-SNAPSHOT**
- **Spring Security** with JWT
- **H2 Database** (development)
- **Gradle** with version catalogs and convention plugins

## Architecture

### Modular Structure
- `app/` - Main Spring Boot application
- `build-logic/` - Gradle convention plugins
- `shared/` - Common utilities (PasswordEncoder config)
- `features/auth/` - JWT logic, filters, and auth endpoints
- `features/user/` - User management and domain models

### Key Components
- **JwtService** - Token generation and validation
- **RefreshTokenService** - Refresh token management
- **JwtAuthFilter** - Spring Security filter for JWT authentication
- **CustomUserDetailsService** - Spring Security integration
- **AuthController** - Authentication REST endpoints

## Getting Started

### Prerequisites
Set these environment variables (or use your IDE):
```bash
JWT_SECRET=your-secret-key-here
JWT_EXPIRATION=900000
JWT_REFRESH_EXPIRATION=604800000
DATABASE_NAME=testdb
DATABASE_USER=test
DATABASE_PASSWORD=test123
```

### Build and Run
```bash
# Build the project
./gradlew build

# Run the application
./gradlew :app:bootRun
```

### Default Users
The application creates default users on startup:
- **Admin**: `admin@example.com` / `admin123Admin!@#`
- **User**: `test@test.com` / `testpassw0rd!@#`

## API Usage

### 1. Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@example.com",
  "password": "admin123Admin!@#"
}
```

### 2. Access Protected Endpoint
```http
GET /api/auth/me
Authorization: Bearer <your-jwt-token>
```

### 3. Refresh Token
```http
POST /api/auth/refresh
Content-Type: application/json

{
  "refreshToken": "your-refresh-token-uuid"
}
```

## Learning Goals

This project demonstrates:
- JWT implementation with Spring Security Authentication Manager
- Refresh token patterns for production applications
- Role-based access control (RBAC)
- Clean architecture with feature-based modules
- Modern Gradle build configuration