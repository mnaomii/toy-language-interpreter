plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

application {
    mainClass = "interpreter.Main"
}

tasks.named<JavaExec>("run") {   // keyboard input for the text menu
    standardInput = System.`in`
    workingDir = rootDir   // test.in and the log files are in the project root
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
