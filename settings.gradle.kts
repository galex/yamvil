enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenLocal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap")
    }
}

rootProject.name = "yamvil"
// The MVI Runtime Library
include(":runtime")
// The MVI Runtime Library with a Channel for UI Actions
include(":runtime-channel")
// Common code for both runtime libraries
include(":runtime-common")
// The Compiler Plugin
include(":compiler-plugin")
include(":compiler-gradle-plugin")
include(":compiler-gradle-dsl")