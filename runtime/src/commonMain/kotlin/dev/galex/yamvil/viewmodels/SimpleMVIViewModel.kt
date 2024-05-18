package dev.galex.yamvil.viewmodels

import dev.galex.yamvil.models.simple.SimpleUiEvent
import dev.galex.yamvil.models.simple.SimpleUiState

class SimpleMVIViewModel: MVIViewModel<SimpleUiState, SimpleUiEvent>() {

    override fun initializeUiState() = SimpleUiState.Initial

    override fun handleEvent(event: SimpleUiEvent) {}
}