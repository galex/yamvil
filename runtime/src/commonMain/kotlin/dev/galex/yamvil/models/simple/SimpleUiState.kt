package dev.galex.yamvil.models.simple

sealed interface SimpleUiState {
    data object Initial: SimpleUiState
}