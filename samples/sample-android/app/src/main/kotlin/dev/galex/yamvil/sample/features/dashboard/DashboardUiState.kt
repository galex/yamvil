package dev.galex.yamvil.sample.features.dashboard

import dev.galex.yamvil.models.base.Actionable
import dev.galex.yamvil.models.base.Consumable

data class DashboardUiState(
    override val action: Consumable<DashboardUiAction>? = null,
    val state: ContentState,
) : Actionable<DashboardUiAction> {

    sealed interface ContentState {
        data object Loading : ContentState
        data object Error : ContentState
        data class Content(
            val title: String,
            val description: String
        ) : ContentState
    }
}