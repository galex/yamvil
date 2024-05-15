package dev.galex.yamvil.models

interface BaseUiState<Action> {
    val action: Consumable<Action>?
}