package dev.galex.yamvil.models.base

interface BaseUiState<Action> {
    val action: Consumable<Action>?
}