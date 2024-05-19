package dev.galex.yamvil.fragments.base

import androidx.annotation.VisibleForTesting
import dev.galex.yamvil.viewmodels.MVIViewModel

interface MVIFragmentInterface<UiState, UiEvent> {

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    val viewModel: MVIViewModel<UiState, UiEvent>

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun UiEvent.send() = viewModel.handleEvent(this)

    fun observeUiState(s: UiState)
}