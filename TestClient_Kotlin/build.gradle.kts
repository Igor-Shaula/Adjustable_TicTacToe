plugins {
    kotlin("jvm") version "1.9.24"
}

group = "org.igor_shaula"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":AtttLibrary"))
}

tasks.test {
    useJUnitPlatform()
}