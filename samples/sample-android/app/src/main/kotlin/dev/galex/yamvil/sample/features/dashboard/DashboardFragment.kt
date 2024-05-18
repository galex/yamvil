package dev.galex.yamvil.sample.features.dashboard

import androidx.fragment.app.viewModels
import dev.galex.yamvil.fragments.base.MVIFragment

class DashboardFragment: MVIFragment<DashboardUiState, DashboardUiEvent, DashboardUiAction>() {

    override val viewModel: DashboardViewModel by viewModels()
    override fun observeUiState(state: DashboardUiState) {
        when(state.state) {
            DashboardUiState.ContentState.Loading -> onLoading()
            is DashboardUiState.ContentState.Content -> onContent(state.state)
            DashboardUiState.ContentState.Error -> onError()
        }

        state.onAction { action ->
            when(action) {
                DashboardUiAction.NavigateToNext -> navigateToNext()
            }
        }
    }

    private fun navigateToNext() {
        // Do something with navigateToNext
    }

    private fun onLoading() {
        // Do something with loading
    }

    private fun onContent(content: DashboardUiState.ContentState.Content) {
        // Do something with content
    }

    private fun onError() {
        // Do something with error
    }
}