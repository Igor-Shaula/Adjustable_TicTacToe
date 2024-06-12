plugins {
    kotlin("jvm") version "1.9.24"
}

group = "org.igor_shaula"
version = "0.7.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}