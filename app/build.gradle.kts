plugins {
    id("kotlin-spring-jwt-learning.spring-boot-app")
}

group = "xyz.powe"
version = "0.0.1-SNAPSHOT"
description = "kotlin-spring-jwt-learning"

dependencies {
    implementation(projects.features.auth)
    implementation(projects.features.user)
    implementation(projects.shared)
}
