plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.igor_shaula"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(files("libs/Adjustable_TicTacToe_Engine-0.2.0.jar"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}