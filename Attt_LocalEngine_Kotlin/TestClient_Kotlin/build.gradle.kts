plugins {
    id("kotlin")
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