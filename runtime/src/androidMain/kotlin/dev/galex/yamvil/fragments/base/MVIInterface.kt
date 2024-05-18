package dev.galex.yamvil.fragments.base

import androidx.annotation.VisibleForTesting
import dev.galex.yamvil.viewmodels.MVIViewModel

interface MVIInterface<UiState, Event, Action> {

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    val viewModel: MVIViewModel<UiState, Event>

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun Event.send() = viewModel.handleEvent(this)

    fun observeUiState(state: UiState)
}