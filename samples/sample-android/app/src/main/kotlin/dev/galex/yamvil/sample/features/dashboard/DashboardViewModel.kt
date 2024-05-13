package dev.galex.yamvil.sample.features.dashboard

import dev.galex.yamvil.viewmodels.MVIViewModel

class DashboardViewModel: MVIViewModel<DashboardUiState, DashboardUiEvent>() {
    override fun handleEvent(event: DashboardUiEvent) {

    }

    override fun initializeUiState(): DashboardUiState {
        return DashboardUiState(state = DashboardUiState.ContentState.Loading)
    }
}