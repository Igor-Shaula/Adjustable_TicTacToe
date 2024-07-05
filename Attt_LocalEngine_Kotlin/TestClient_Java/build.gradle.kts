plugins {
    id("java")
}

group = "org.igor_shaula"

repositories {
    mavenCentral()
}

dependencies {
    project(":AtttLibrary")
//    implementation(project(":utilities"))
}

tasks.test {
    useJUnitPlatform()
}