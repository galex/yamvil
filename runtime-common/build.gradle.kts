plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {

    
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

    sourceSets {
        commonMain.dependencies {
            api(libs.lifecyle.viewmodel.compose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
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
