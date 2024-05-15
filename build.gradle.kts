buildscript {
    extra["kotlin_plugin_id"] = "dev.galex.yamvil"
}

plugins {
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)

    alias(libs.plugins.kotlin.jvm) apply false
    id("org.jetbrains.dokka") version "1.7.0" apply false
    id("com.gradle.plugin-publish") version "1.0.0" apply false
    id("com.github.gmazzo.buildconfig") version "3.1.0" apply false
}

allprojects {
    group = "dev.galex.yamvil"
    version = "0.0.1"
}
