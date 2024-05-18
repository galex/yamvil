package dev.galex.yamvil.fragments.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import dev.galex.yamvil.extensions.observeStateFlow
import dev.galex.yamvil.models.base.BaseUiState
import dev.galex.yamvil.viewmodels.MVIViewModel

/**
 * Base class for Fragments that use MVI architecture.
 * @param UiState The UiState class that the ViewModel uses.
 * @param Event The Event class that the ViewModel uses.
 * @param Action The Action class that the ViewModel uses.
 */
abstract class MVIFragment<UiState: BaseUiState<*>, Event, Action>(
    @LayoutRes contentLayoutId: Int = 0,
): Fragment(contentLayoutId) {

    @VisibleForTesting
    abstract val viewModel: MVIViewModel<UiState, Event>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStateFlow(viewModel.uiState, ::observeUiState, ::consumeAction)
    }

    @VisibleForTesting
    fun Event.send() = viewModel.handleEvent(this)

    protected open fun observeUiState(state: UiState) {}

    protected open fun consumeAction(action: Action) {}
}