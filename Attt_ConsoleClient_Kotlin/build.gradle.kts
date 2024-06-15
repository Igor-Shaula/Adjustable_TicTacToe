plugins {
    kotlin("jvm") version "1.9.24"
}

group = "org.igor_shaula"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(files("libs/Attt_LocalEngine_Kotlin-0.7.4.jar"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}