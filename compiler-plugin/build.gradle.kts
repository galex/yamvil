plugins {
    alias(libs.plugins.kotlin.jvm)
    kotlin("kapt")
    id("com.github.gmazzo.buildconfig")
    `maven-publish`
}

dependencies {

    libs.kotlin.compiler.let {
        compileOnly(it)
        testImplementation(it)
    }

    testRuntimeOnly(libs.kotlin.test)
    testRuntimeOnly(libs.kotlin.script.runtime)
    testRuntimeOnly(libs.kotlin.annotations.jvm)

    testImplementation(libs.kotlin.reflect)
    testImplementation(libs.kotlin.compiler.internal.test.framework)
    testImplementation(libs.junit)


    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.platform.commons)
    testImplementation(libs.junit.jupiter.platform.launcher)
    testImplementation(libs.junit.jupiter.platform.runner)
    testImplementation(libs.junit.jupiter.platform.suite.api)

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

publishing {
    publications {
        create<MavenPublication>("compilerPlugin") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            from(components["java"])
            artifact(tasks.kotlinSourcesJar)
        }
    }
}

sourceSets {
    test {
        java.srcDirs("src/test-gen")
    }
}

tasks.create<JavaExec>("generateTests") {
    group = "test"
    classpath = sourceSets.test.get().runtimeClasspath
    mainClass.set("dev.galex.yamvil.GenerateTestsKt")
}

tasks.test {
    testLogging {
        showStandardStreams = true
    }

    useJUnitPlatform()
    doFirst {
        setLibraryProperty("org.jetbrains.kotlin.test.kotlin-stdlib", "kotlin-stdlib")
        setLibraryProperty("org.jetbrains.kotlin.test.kotlin-stdlib-jdk8", "kotlin-stdlib-jdk8")
        setLibraryProperty("org.jetbrains.kotlin.test.kotlin-reflect", "kotlin-reflect")
        setLibraryProperty("org.jetbrains.kotlin.test.kotlin-test", "kotlin-test")
        setLibraryProperty("org.jetbrains.kotlin.test.kotlin-script-runtime", "kotlin-script-runtime")
        setLibraryProperty("org.jetbrains.kotlin.test.kotlin-annotations-jvm", "kotlin-annotations-jvm")
    }

    dependsOn(":dsl:jar")
}

fun Test.setLibraryProperty(propName: String, jarName: String) {
    val path = project.configurations
        .testRuntimeClasspath.get()
        .files
        .find { """$jarName-\d.*jar""".toRegex().matches(it.name) }
        ?.absolutePath
        ?: return
    systemProperty(propName, path)
}
