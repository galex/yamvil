# Yamvil

[![Maven Central Version](https://img.shields.io/maven-central/v/dev.galex.yamvil/runtime)](https://central.sonatype.com/search?q=dev.galex.yamvil)
[![CI](https://github.com/galex/yamvil/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/galex/yamvil/actions/workflows/ci.yml)
[![codecov](https://codecov.io/github/galex/yamvil/branch/main/graph/badge.svg?token=ML8EN8PYP0)](https://codecov.io/github/galex/yamvil)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Yamvil is a library and a compiler plugin bringing MVI Infrastructure to ViewModels, Fragments and Jetpack Compose, for Android and Compose Multiplatform apps.

> ⚠️ **This is a work in progress**: Yamvil is still in early development and is not ready for production use. The API is subject to change and only Android Studio Nightly shows the errors thrown by the compiler plugin.

## Documentation

The full documentation of Yamvil [is available here](https://docs.galex.dev/yamvil/).

## To the Point

Write MVI ViewModels like this:
```kotlin
class DashboardViewModel: MVIViewModel<DashboardUiState, DashboardUiEvent>() {

    override fun initializeUiState(): DashboardUiState {
        return DashboardUiState(state = DashboardUiState.ContentState.Loading)
    }
    override fun handleEvent(event: DashboardUiEvent) {
        when (event) {
            is DashboardUiEvent.ClickOnNext -> onClickOnNext()
        }
    }

    private fun onClickOnNext() {
        update { copy(action = Consumable(DashboardUiAction.NavigateToNext)) }
    }
}
```
Then use your MVI ViewModel in a Composable like this:
```kotlin
@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    handleEvent: (DashboardUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState.state) {
        is DashboardUiState.ContentState.Loading -> DashboardLoadingContent()
        is DashboardUiState.ContentState.Error -> DashboardErrorContent()
        is DashboardUiState.ContentState.Content -> DashboardContent(uiState.state)
    }

    LaunchedActionEffect(uiState) { action: DashboardUiAction ->
        when (action) {
            DashboardUiAction.NavigateToNext -> {}
        }
    }
}
// (...)
```
Or if you're using Fragments, like this:
```kotlin
class DashboardFragment: MVIFragment<DashboardUiState, DashboardUiEvent>() {

    override val viewModel: DashboardViewModel by viewModels()
    override fun observeUiState(uiState: DashboardUiState) {
        when (uiState.state) {
            DashboardUiState.ContentState.Loading -> onLoading()
            is DashboardUiState.ContentState.Content -> onContent(uiState.state)
            DashboardUiState.ContentState.Error -> onError()
        }

        uiState.onAction { action ->
            when (action) {
                DashboardUiAction.NavigateToNext -> navigateToNext()
            }
        }
    }
    // (...)
}
```

## Installation

In your libs.version.toml, add the following:

```toml
[versions]
yamvil = "0.0.2"

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

```kotlin
yamvil {
    level = YamvilLevel.Error // or Warning
    compose {
        screenSuffix = "Screen"
        uiStateParameterName = "uiState"
        handleEventParameterMame = "onEvent"
    }
}
```
## Publishing

Run `./gradlew publishToMavenLocal` to publish all the artefacts locally.

Run `./gradlew publishAndReleaseToMavenCentral --no-configuration-cache` to publish all artefacts to Maven Central.