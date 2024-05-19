package dev.galex.yamvil.fragments.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import dev.galex.yamvil.extensions.observeActionFlow
import dev.galex.yamvil.runtime.common.extensions.observeStateFlow

/**
 * Base class for Fragments that use MVI architecture.
 * @param UiState The UiState class that the ViewModel uses.
 * @param UiEvent The Event class that the ViewModel uses.
 * @param UiAction The Action class that the ViewModel uses.
 */
abstract class MVIChannelDialogFragment<UiState, UiEvent, UiAction>(
    @LayoutRes contentLayoutId: Int = 0,
) : DialogFragment(contentLayoutId), MVIChannelFragmentInterface<UiState, UiEvent, UiAction> {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStateFlow(this.viewModel.uiState, ::observeUiState)
        observeActionFlow(this.viewModel.uiAction, ::consumeAction)
    }
}