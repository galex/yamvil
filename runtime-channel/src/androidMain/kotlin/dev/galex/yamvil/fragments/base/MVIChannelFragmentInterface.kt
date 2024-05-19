package dev.galex.yamvil.fragments.base

import androidx.annotation.VisibleForTesting
import dev.galex.yamvil.viewmodels.MVIChannelViewModel

interface MVIChannelFragmentInterface<UiState, UiEvent, UiAction> {

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    val viewModel: MVIChannelViewModel<UiState, UiEvent, UiAction>

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun UiEvent.send() = viewModel.handleEvent(this)

    fun observeUiState(state: UiState)

    fun consumeAction(action: UiAction)
}