package dev.galex.yamvil.models.simple

import dev.galex.yamvil.models.base.BaseUiState
import dev.galex.yamvil.models.base.Consumable

sealed class SimpleUiState: BaseUiState<Unit> {
    override val action: Consumable<Unit>? = null
    data object Initial: SimpleUiState()
}