plugins {
    kotlin("jvm") version "2.0.0-RC3"
    id("dev.galex.yamvil") version "0.0.2"
}

group = "dev.galex.yamvil.sample.jvm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
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