plugins {
  id("java-gradle-plugin")
  kotlin("jvm")
  id("com.github.gmazzo.buildconfig")
  `maven-publish`
}

dependencies {
  implementation(kotlin("gradle-plugin-api"))
  implementation("com.google.code.gson:gson:2.9.1")
  implementation(projects.compilerGradleDsl)

  testImplementation("com.google.truth:truth:1.4.2")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

buildConfig {
  val compilerProject = projects.compilerPlugin
  packageName(compilerProject.group.toString())
  buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"${rootProject.extra["kotlin_plugin_id"]}\"")
  buildConfigField("String", "KOTLIN_PLUGIN_GROUP", "\"${compilerProject.group}\"")
  buildConfigField("String", "KOTLIN_COMPILER_PLUGIN_NAME", "\"${compilerProject.name}\"")
  buildConfigField("String", "KOTLIN_PLUGIN_VERSION", "\"${compilerProject.version}\"")
/*
  val dslProject = projects.dsl
  buildConfigField("String", "DSL_LIBRARY_GROUP", "\"${dslProject.group}\"")
  buildConfigField("String", "DSL_LIBRARY_NAME", "\"${dslProject.name}\"")
  buildConfigField("String", "DSL_LIBRARY_VERSION", "\"${dslProject.version}\"")
*/
}

gradlePlugin {
  plugins {
    create("YamvilGradlePlugin") {
      id = rootProject.extra["kotlin_plugin_id"] as String
      displayName = "Yamvil Gradle Plugin"
      description = "MVI the right way at compile time"
      implementationClass = "dev.galex.yamvil.gradle.YamvilGradlePlugin"
    }
  }
}

publishing {
  publications {
    create<MavenPublication>("gradlePlugin") {
      groupId = project.group.toString()
      artifactId = project.name
      version = project.version.toString()
      from(components["java"])
      artifact(tasks.kotlinSourcesJar)
    }
  }
}
