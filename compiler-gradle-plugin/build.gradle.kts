import com.vanniktech.maven.publish.SonatypeHost

plugins {
  id("java-gradle-plugin")
  //alias(libs.plugins.java.gradle.plugin)
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.buildconfig)
  alias(libs.plugins.vanniktech.maven.publish)
}

dependencies {
  implementation(kotlin("gradle-plugin-api"))
  implementation(libs.gson)
  implementation(projects.compilerGradleDsl)
}

buildConfig {
  val compilerProject = projects.compilerPlugin
  packageName(compilerProject.group.toString())
  buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"${rootProject.extra["kotlin_plugin_id"]}\"")
  buildConfigField("String", "KOTLIN_PLUGIN_GROUP", "\"${compilerProject.group}\"")
  buildConfigField("String", "KOTLIN_COMPILER_PLUGIN_NAME", "\"${compilerProject.name}\"")
  buildConfigField("String", "KOTLIN_PLUGIN_VERSION", "\"${compilerProject.version}\"")
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

mavenPublishing {
  publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
  signAllPublications()

  pom {
    name.set("Yamvil Compiler Gradle Plugin")
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

