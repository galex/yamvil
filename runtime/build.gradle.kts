import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kover)
    alias(libs.plugins.vanniktech.maven.publish)
}

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
        testImplementation(libs.compose.ui.test.junit)
        debugImplementation(libs.compose.ui.test.manifest)
        testImplementation(libs.compose.material3)
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
//
mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    pom {
        name.set("Yamvil Runtime Library")
        description.set("Yamvil is a library that helps you to write MVI the right way at compile time")
        inceptionYear.set("2024")
        url.set("https://github.com/galex/yamvil")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("galex")
                name.set("Alexander Gherschon")
                url.set("https://galex.dev/")
            }
        }
        scm {
            url.set("https://github.com/galex/yamvil")
            connection.set("scm:git:git://github.com/galex/yamvil.git")
            developerConnection.set("scm:git:ssh://git@github.com/galex/yamvil.git")
        }
    }
}