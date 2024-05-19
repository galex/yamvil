package dev.galex.yamvil.viewmodels

import dev.galex.yamvil.models.simple.SimpleUiAction
import dev.galex.yamvil.models.simple.SimpleUiEvent
import dev.galex.yamvil.models.simple.SimpleUiState

class SimpleMVIChannelViewModel: MVIChannelViewModel<SimpleUiState, SimpleUiEvent, SimpleUiAction>() {

    override fun initializeUiState() = SimpleUiState.Initial

    override fun handleEvent(event: SimpleUiEvent) {}
}