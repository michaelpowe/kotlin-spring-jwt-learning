plugins {
    id("kotlin-spring-jwt-learning.spring-boot-app")
}

group = "xyz.powe"
version = "0.0.1-SNAPSHOT"
description = "kotlin-spring-jwt-learning"

dependencies {
    implementation(libs.spring.boot.h2console)
    implementation(projects.features.auth)
    implementation(projects.features.user)
    implementation(projects.shared)

    runtimeOnly(libs.h2.database)
}
