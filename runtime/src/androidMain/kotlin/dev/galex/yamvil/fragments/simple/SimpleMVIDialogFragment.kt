package dev.galex.yamvil.fragments.simple

import androidx.fragment.app.viewModels
import dev.galex.yamvil.fragments.base.MVIDialogFragment
import dev.galex.yamvil.models.simple.SimpleUiEvent
import dev.galex.yamvil.models.simple.SimpleUiState
import dev.galex.yamvil.viewmodels.SimpleMVIViewModel

/**
 * A simple MVI dialog fragment.
 */
class SimpleMVIDialogFragment: MVIDialogFragment<SimpleUiState, SimpleUiEvent>() {

    override val viewModel: SimpleMVIViewModel by viewModels()

    override fun observeUiState(state: SimpleUiState) { /* NO-OP */ }
}