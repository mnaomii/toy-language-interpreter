plugins {
    // downloads JDK 25 automatically if the machine does not have it
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "toy-language-interpreter"
include("app")
