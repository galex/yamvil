package dev.galex.yamvil.sample.features.dashboard

import dev.galex.yamvil.models.BaseUiState
import dev.galex.yamvil.models.Consumable
import dev.galex.yamvil.sample.features.dashboard.DashboardUiAction


data class DashboardUiState(
    override val action: Consumable<DashboardUiAction>? = null,
    val state: ContentState,
) : BaseUiState<DashboardUiAction> {

    sealed interface ContentState {
        data object Loading : ContentState
        data object Error : ContentState
        data class Content(
            val title: String,
            val description: String
        ) : ContentState
    }
}