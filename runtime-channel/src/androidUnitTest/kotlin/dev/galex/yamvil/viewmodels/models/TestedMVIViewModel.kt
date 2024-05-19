package dev.galex.yamvil.viewmodels.models

import dev.galex.yamvil.viewmodels.MVIChannelViewModel

class TestedViewModel: MVIChannelViewModel<TestedUiState, TestedUiEvent, TestedUiAction>() {

    override fun initializeUiState() = TestedUiState()

    override fun handleEvent(event: TestedUiEvent) {
        when (event) {
            is TestedUiEvent.FirstEvent -> update { copy(firstEventTriggered = true) }
            is TestedUiEvent.SecondEvent -> update { copy(secondEventTriggered = true) }
            is TestedUiEvent.ThirdEvent -> send(TestedUiAction.FirstAction)
        }
    }
}