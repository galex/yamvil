plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("maven-publish")
}

group = "dev.galex.yamvil"
version = "0.0.3"

kotlin {
    androidTarget {
        compilations.all {
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

    dependencies {
        implementation(libs.androidx.fragment.ktx)
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