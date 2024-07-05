plugins {
    id("java")
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