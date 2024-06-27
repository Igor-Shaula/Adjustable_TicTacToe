plugins {
    kotlin("jvm") version "2.0.0"
}

group = "org.igor_shaula"
version = "0.8.0"

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