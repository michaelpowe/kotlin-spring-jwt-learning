plugins {
    alias(libs.plugins.kotlin.jpa) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.dependency.management) apply false
}

group = "xyz.powe"
version = "0.0.1-SNAPSHOT"
description = "kotlin-spring-jwt-learning"

subprojects {
    group = rootProject.group
    version = rootProject.version
}