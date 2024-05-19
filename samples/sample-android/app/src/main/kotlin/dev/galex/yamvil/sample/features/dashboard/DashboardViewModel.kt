package dev.galex.yamvil.sample.features.dashboard

import dev.galex.yamvil.models.base.Consumable
import dev.galex.yamvil.viewmodels.MVIViewModel

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