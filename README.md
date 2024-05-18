# Yamvil

[![CI](https://github.com/galex/yamvil/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/galex/yamvil/actions/workflows/ci.yml)
[![codecov](https://codecov.io/github/galex/yamvil/branch/main/graph/badge.svg?token=ML8EN8PYP0)](https://codecov.io/github/galex/yamvil)

Yamvil is a library and a compiler plugin to help you write MVI Screens in the easiest way possible on Android and Compose Multiplatform.

## Documentation

The full documentation of Yamvil [is available right here](https://docs.galex.dev/yamvil/).

## Introduction

Yamvil is a tool built to help us write Screens that follow the MVI Pattern on Android and Compose Multiplatform.

## Installation

In your libs.version.toml, add the following:

```toml
[versions]
yamvil = "0.0.1"

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
## Contributing

Run `./gradlew publishToMavenLocal` to publish all the artefacts locally.