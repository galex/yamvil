plugins {
    kotlin("jvm")
    `maven-publish`
}

dependencies {
    testImplementation("com.google.truth:truth:1.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

publishing {
    publications {
        create<MavenPublication>("dsl") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            from(components["java"])
            artifact(tasks.kotlinSourcesJar)
        }
    }
}