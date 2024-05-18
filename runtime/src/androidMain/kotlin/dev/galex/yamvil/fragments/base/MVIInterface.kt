package dev.galex.yamvil.fragments.base

import androidx.annotation.VisibleForTesting
import dev.galex.yamvil.models.base.BaseUiState
import dev.galex.yamvil.viewmodels.MVIViewModel

interface MVIInterface<UiState: BaseUiState<*>, Event, Action> {

    @VisibleForTesting
    val viewModel: MVIViewModel<UiState, Event>

    @VisibleForTesting
    fun Event.send() = viewModel.handleEvent(this)

    fun observeUiState(state: UiState)
}