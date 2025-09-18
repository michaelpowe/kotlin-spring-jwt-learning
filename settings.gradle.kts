pluginManagement {
    includeBuild("build-logic")
    repositories {
        maven { url = uri("https://repo.spring.io/snapshot") }
        gradlePluginPortal()
    }
}
rootProject.name = "kotlin-spring-jwt-learning"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include("app")
include("shared")
include("features:auth")
include("features:user")
