package dev.galex.yamvil.sample.features.dashboard

sealed interface DashboardUiEvent {
    data object ClickOnNext: DashboardUiEvent
}