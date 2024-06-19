plugins {
    kotlin("jvm") version "2.0.0"
}

group = "org.igor_shaula"
version = "0.7.6"

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