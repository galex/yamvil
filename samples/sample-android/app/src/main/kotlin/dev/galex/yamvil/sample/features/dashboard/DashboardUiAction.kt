package dev.galex.yamvil.sample.features.dashboard

sealed interface DashboardUiAction {
    data object NavigateToNext : DashboardUiAction
}