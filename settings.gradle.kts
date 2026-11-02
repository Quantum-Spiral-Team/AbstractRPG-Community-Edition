pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.cleanroommc.com")
        }
        gradlePluginPortal()
    }
}

plugins {
    id("com.cleanroommc.cleanroomgradle.settings") version "0.22.0"
}
