package dev.galex.mvi.models

interface BaseUiState<Action> {
    val action: Consumable<Action>?
}