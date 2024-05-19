package dev.galex.yamvil.sample.features.dashboard

import androidx.fragment.app.viewModels
import dev.galex.yamvil.fragments.base.MVIFragment

class DashboardFragment: MVIFragment<DashboardUiState, DashboardUiEvent>() {

    override val viewModel: DashboardViewModel by viewModels()
    override fun observeUiState(uiState: DashboardUiState) {
        when(uiState.state) {
            DashboardUiState.ContentState.Loading -> onLoading()
            is DashboardUiState.ContentState.Content -> onContent(uiState.state)
            DashboardUiState.ContentState.Error -> onError()
        }

        uiState.onAction { action ->
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