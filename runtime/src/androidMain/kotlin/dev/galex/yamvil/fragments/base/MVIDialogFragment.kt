package dev.galex.yamvil.fragments.base
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import dev.galex.yamvil.extensions.observeStateFlow

/**
 * Base class for DialogFragments that use MVI architecture.
 * @param UiState The UiState class that the ViewModel uses.
 * @param UiEvent The Event class that the ViewModel uses.
 */
abstract class MVIDialogFragment<UiState, UiEvent>(
    @LayoutRes contentLayoutId: Int = 0,
) : DialogFragment(contentLayoutId), MVIFragmentInterface<UiState, UiEvent> {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStateFlow(viewModel.uiState, ::observeUiState)
    }
}