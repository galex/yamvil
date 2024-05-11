package dev.galex.mvi.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dev.galex.mvi.models.BaseUiState
import dev.galex.mvi.viewmodels.MVIViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

abstract class MVIFragment<UiState, Event, Action>: Fragment() {

    protected abstract val viewModel: MVIViewModel<UiState, Event>

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state: UiState ->
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

    protected fun Event.send() = viewModel.handleEvent(this)

    protected fun observeUiState(state: UiState) {}

    protected fun consumeAction(consume: Action) {}
}