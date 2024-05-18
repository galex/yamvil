package dev.galex.yamvil.viewmodels.models

import dev.galex.yamvil.models.base.Consumable
import dev.galex.yamvil.viewmodels.MVIViewModel

class TestedViewModel: MVIViewModel<TestedUiState, TestedUiEvent>() {

    override fun initializeUiState() = TestedUiState()

    override fun handleEvent(event: TestedUiEvent) {
        when (event) {
            is TestedUiEvent.FirstEvent -> update { copy(firstEventTriggered = true) }
            is TestedUiEvent.SecondEvent -> update { copy(secondEventTriggered = true) }
            is TestedUiEvent.ThirdEvent -> update { copy(action = Consumable(TestedUiAction.FirstAction)) }
        }
    }
}