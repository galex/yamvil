import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kover)
    alias(libs.plugins.vanniktech.maven.publish)
}

dependencies {
    testImplementation(libs.truth)
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    pom {
        name.set("Yamvil Compiler Gradle DSL")
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
