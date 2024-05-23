import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    kotlin("kapt")
    alias(libs.plugins.buildconfig)
    alias(libs.plugins.vanniktech.maven.publish)
    alias(libs.plugins.kover)
}

dependencies {

    libs.kotlin.compiler.let {
        compileOnly(it)
        testImplementation(it)
    }

    testImplementation(kotlin("test-junit"))

//    testRuntimeOnly(libs.kotlin.test)
//    testRuntimeOnly(libs.kotlin.script.runtime)
//    testRuntimeOnly(libs.kotlin.annotations.jvm)

    // testImplementation(libs.kotlin.reflect)
    //testImplementation(libs.kotlin.compiler.internal.test.framework)
    //testImplementation(libs.junit)
    //testImplementation(libs.junit)


    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.platform.commons)
    testImplementation(libs.junit.jupiter.platform.launcher)
    testImplementation(libs.junit.jupiter.platform.runner)
    testImplementation(libs.junit.jupiter.platform.suite.api)
    testImplementation(libs.truth)

    kapt(libs.autoservice)
    compileOnly(libs.autoservice.annotations)

    testImplementation(projects.compilerGradleDsl)
    testImplementation(libs.kotlin.compile.testing)

    implementation(projects.compilerGradleDsl)
    implementation(libs.gson)
}

buildConfig {
    packageName(group.toString())
    buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"${rootProject.extra["kotlin_plugin_id"]}\"")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs += listOf(
        "-opt-in=org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi",
    )
}

tasks.withType<Test> {
    useJUnitPlatform()
}

//sourceSets {
//    test {
//        java.srcDirs("src/test-gen")
//    }
//}
//
//tasks.create<JavaExec>("generateTests") {
//    group = "test"
//    classpath = sourceSets.test.get().runtimeClasspath
//    mainClass.set("dev.galex.yamvil.GenerateTestsKt")
//}
//
//tasks.test {
//    dependsOn(project(":compiler-gradle-dsl").tasks.getByName("jar"))
//    dependsOn(project(":runtime").tasks.getByName("compileDebugSources"))
//    useJUnitPlatform()
//    doFirst {
//        setLibraryProperty("org.jetbrains.kotlin.test.kotlin-stdlib", "kotlin-stdlib")
//        setLibraryProperty("org.jetbrains.kotlin.test.kotlin-stdlib-jdk8", "kotlin-stdlib-jdk8")
//        setLibraryProperty("org.jetbrains.kotlin.test.kotlin-reflect", "kotlin-reflect")
//        setLibraryProperty("org.jetbrains.kotlin.test.kotlin-test", "kotlin-test")
//        setLibraryProperty("org.jetbrains.kotlin.test.kotlin-script-runtime", "kotlin-script-runtime")
//        setLibraryProperty("org.jetbrains.kotlin.test.kotlin-annotations-jvm", "kotlin-annotations-jvm")
//    }
//
//
//}
//
//fun Test.setLibraryProperty(propName: String, jarName: String) {
//    val path = project.configurations
//        .testRuntimeClasspath.get()
//        .files
//        .find { """$jarName-\d.*jar""".toRegex().matches(it.name) }
//        ?.absolutePath
//        ?: return
//    systemProperty(propName, path)
//}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    pom {
        name.set("Yamvil Compiler Plugin")
        description.set("Yamvil is a library that helps you to write MVI Screens the right way at compile time")
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
