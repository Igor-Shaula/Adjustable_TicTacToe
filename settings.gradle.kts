plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "Attt_LocalEngine_Kotlin"

include("AtttLibrary")
include("TestClient_Kotlin")
include("TestClient_Java")
