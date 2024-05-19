plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kover)
    id("maven-publish")
}

//group = "dev.galex.yamvil"
//version = "0.0.3"

kotlin {
    androidTarget {
        compilations.all {
            @Suppress("DEPRECATION")
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
        publishLibraryVariants("release", "debug")
    }
    
    /*val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "mvi"
            xcf.add(this)
            isStatic = true
        }
    }*/

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "yamvil"
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.lifecyle.viewmodel.compose)
            api(projects.runtimeCommon)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "dev.galex.yamvil"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    dependencies {
        api(libs.androidx.fragment.ktx)
        testImplementation(libs.robolectric)
        testImplementation(libs.truth)
        testDebugImplementation(libs.androidx.fragment.testing)
    }
}

kover {
    reports {
        filters {
            excludes {
                classes(
                    "dev.galex.yamvil.fragments.simple.*",
                    "dev.galex.yamvil.models.simple.*",
                    "dev.galex.yamvil.viewmodels.SimpleMVIViewModel",
                )
            }
        }
    }
}

//afterEvaluate {
//    publishing {
//        publications.configureEach {
//            if (this is MavenPublication) {
//                groupId = project.group.toString()
//                artifactId = artifactId.replace(project.name, "runtime")
//                version = project.version.toString()
//            }
//        }
//    }
//}