plugins {
    id("java-library")
    id("kotlin-spring-jwt-learning.spring-boot-service")
    kotlin("plugin.jpa")
    }

group = "xyz.powe"
version = "0.0.1-SNAPSHOT"
description = "kotlin-spring-jwt-learning"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation(projects.shared)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}