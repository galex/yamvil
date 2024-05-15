# Yamvil - MVI Tbe Right Way

> ⚠️ This is a work in progress 

## Introduction

This is a Library and Compiler Plugin to help you write Screens that follow the MVI Pattern on Android and Compose Multiplatform.

- ✅ MVIViewModel as a base ViewModel class that requires an `UiState`, `UiEvent` and `UiAction` classes
- ✅ Built on top of [ViewModel of Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-lifecycle.html#viewmodel-implementation)
- ✅ Avoids [the trap mentioned by Google here](https://medium.com/androiddevelopers/viewmodel-one-off-event-antipatterns-16a1da869b95) by using `BaseUIState` that contains a `Consumable<UiAction>` for one-shot events
- ✅ Supports Android Fragments via inheritance of MVIFragment
- ✅ Checks for correct implementation of @Composable at **Compile Time**
- ✅ Autocompletes your IDE when writing a new Screen

## Configuration

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
## Demo 

// TBD Make a video when this is working in the IDE again

## Contributing

Run `./gradlew publishToMavenLocal` to publish all the artefacts locally