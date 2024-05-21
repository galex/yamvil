# Yamvil

[![Maven Central Version](https://img.shields.io/maven-central/v/dev.galex.yamvil/runtime)](https://central.sonatype.com/search?q=dev.galex.yamvil)
[![CI](https://github.com/galex/yamvil/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/galex/yamvil/actions/workflows/ci.yml)
[![codecov](https://codecov.io/github/galex/yamvil/branch/main/graph/badge.svg?token=ML8EN8PYP0)](https://codecov.io/github/galex/yamvil)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

> Do we really need Yet Another MVI Library? Yes, we do!

Yamvil is a library and a compiler plugin to help write MVI Screens ensuring a proper Unidirectional Data Flow way for Android and Compose Multiplatform.

> ⚠️ **This is a work in progress**: Yamvil is still in early development and is not ready for production use. The API is subject to change and Android Studio is not ready to run the compiler plugin, **yet**.

## Documentation

The full documentation of Yamvil [is available right here](https://docs.galex.dev/yamvil/).

## Quick Start

In your libs.version.toml, add the following:

```toml
[versions]
yamvil = "<latest version>"

[libraries]
yamvil = { group = "dev.galex.yamvil", name = "runtime", version.ref = "yamvil" }

[plugins]
yamvil = { id = "dev.galex.yamvil", version.ref = "yamvil" }
```

Then add those to your project:
```kotlin
// Root build.gradle.kts
plugins {
    alias(libs.plugins.yamvil) apply false
}
// In your app/build.gradle.kts
plugins {
    alias(libs.plugins.yamvil)
}

dependencies {
    implementation(libs.yamvil)
}
```
## Configuration

By default, anything yamvil have to tell you will be an as an error.
You can change it to Warning:

```kotlin
yamvil {
    level = YamvilLevel.Warning
}
```
## Publishing

Run `./gradlew publishToMavenLocal` to publish all the artefacts locally.

Run `./gradlew publishAndReleaseToMavenCentral --no-configuration-cache` to publish all artefacts to Maven Central.