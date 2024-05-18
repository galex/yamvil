package dev.galex.yamvil.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Observes the state flow and calls the observeUiState function when the state changes.
 * @param stateFlow The state flow to observe.
 * @param observeUiState The function to call when the state changes.
 */
fun <UiState> Fragment.observeStateFlow(
    stateFlow: Flow<UiState>,
    observeUiState: (UiState) -> Unit,
) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
            stateFlow.collect { state: UiState ->
                observeUiState(state)
            }
        }
    }
}