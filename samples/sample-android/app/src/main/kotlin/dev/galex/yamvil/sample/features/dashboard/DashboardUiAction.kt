package dev.galex.yamvil.sample.features.dashboard

sealed class DashboardUiAction {
    
    data object NavigateToNext : DashboardUiAction()
}