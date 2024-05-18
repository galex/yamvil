package dev.galex.yamvil.viewmodels.models

import dev.galex.yamvil.models.base.Actionable
import dev.galex.yamvil.models.base.Consumable

data class TestedUiState(
    override val action: Consumable<TestedUiAction>? = null,
    var firstEventTriggered: Boolean = false,
    var secondEventTriggered: Boolean = false,
): Actionable<TestedUiAction>