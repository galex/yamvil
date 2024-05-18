# Yamvil - Writing MVI Screens Made Easy

[![CI](https://github.com/galex/yamvil/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/galex/yamvil/actions/workflows/ci.yml)
[![codecov](https://codecov.io/github/galex/yamvil/branch/main/graph/badge.svg?token=ML8EN8PYP0)](https://codecov.io/github/galex/yamvil)

## ⚠️ Work In Progress ⚠️

The runtime library is usable and I hope you'll bring feedback!

The Compiler Plugin is usable by the compiler itself but warnings and errors cannot be seen within the IDE, yet
- K2 IDE Plugin is in Alpha but should show warnings/errors
- Android Studio is bundled with an older version of the Compose Compiler Plugin, [more info here](https://issuetracker.google.com/issues/341233001)

## Introduction

Yamvil is a tool built to help us write Screens that follow the MVI Pattern on Android and Compose Multiplatform.

- ✅ [MVIViewModel](https://github.com/galex/yamvil/blob/main/runtime/src/commonMain/kotlin/dev/galex/yamvil/viewmodels/MVIViewModel.kt) as a base ViewModel class that requires an `UiState`, `UiEvent` and `UiAction` classes
- ✅ Built on top of [ViewModel of Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-lifecycle.html#viewmodel-implementation)
- ✅ Avoids [losing any events](https://medium.com/androiddevelopers/viewmodel-one-off-event-antipatterns-16a1da869b95) by using a `Consumable` class built into a `BaseUIState` for one-off events
- ✅ Supports Android Fragments via inheritance of [MVIFragment](https://github.com/galex/yamvil/blob/main/runtime/src/androidMain/kotlin/dev/galex/yamvil/fragments/MVIFragment.kt)
- ✅ Checks for correct implementation of MVI in @Composable function which name finishes by "Screen"

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