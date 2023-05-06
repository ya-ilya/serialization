rootProject.name = "serialization"

pluginManagement {
    repositories {
        mavenCentral()
    }

    plugins {
        val kotlinVersion: String by settings

        kotlin("jvm") version kotlinVersion
    }
}

include(":serialization-core")
project(":serialization-core").projectDir = file("./core")