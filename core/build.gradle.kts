val projectVersion: String by project
val kotlinVersion: String by project

plugins {
    kotlin("jvm")
    id("maven-publish")
}

group = "me.yailya"
version = projectVersion

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "me.yailya"
            artifactId = "serialization"
            version = projectVersion

            artifact(tasks.jar) {
                this.classifier = "core"
            }
        }
    }
}