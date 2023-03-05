plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "sk.emanuelzaymus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/com.jakewharton.fliptables/fliptables
    implementation("com.jakewharton.fliptables:fliptables:1.0.2")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}