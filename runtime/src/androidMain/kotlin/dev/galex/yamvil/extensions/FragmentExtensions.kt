

package dev.galex.yamvil.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dev.galex.yamvil.models.base.BaseUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
fun <UiState: BaseUiState<*>, Action> Fragment.observeStateFlow(
    stateFlow: Flow<UiState>,
    observeUiState: (UiState) -> Unit,
    consumeAction: (Action) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
            stateFlow.collect { state: UiState ->
                observeUiState(state)
                val uiState = state as? BaseUiState<*> ?: return@collect
                val action = uiState.action ?: return@collect
                if (action.consumed.not()) {
                    consumeAction(state.action!!.consume() as Action)
                }
            }
        }
    }
}