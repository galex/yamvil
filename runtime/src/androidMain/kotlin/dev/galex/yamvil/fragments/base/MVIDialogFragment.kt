package dev.galex.yamvil.fragments.base
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import dev.galex.yamvil.extensions.observeStateFlow

/**
 * Base class for DialogFragments that use MVI architecture.
 * @param UiState The UiState class that the ViewModel uses.
 * @param Event The Event class that the ViewModel uses.
 * @param Action The Action class that the ViewModel uses.
 */
abstract class MVIDialogFragment<UiState, Event, Action>(
    @LayoutRes contentLayoutId: Int = 0,
) : DialogFragment(contentLayoutId), MVIInterface<UiState, Event, Action> {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStateFlow(viewModel.uiState, ::observeUiState)
    }
}