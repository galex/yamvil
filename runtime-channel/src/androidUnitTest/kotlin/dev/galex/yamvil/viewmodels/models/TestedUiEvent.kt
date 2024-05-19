package dev.galex.yamvil.viewmodels.models

sealed interface TestedUiEvent {
    object FirstEvent: TestedUiEvent
    object SecondEvent: TestedUiEvent
    object ThirdEvent: TestedUiEvent
}