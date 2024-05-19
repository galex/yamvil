package dev.galex.yamvil.fragments.simple

import androidx.fragment.app.viewModels
import dev.galex.yamvil.fragments.base.MVIChannelDialogFragment
import dev.galex.yamvil.models.simple.SimpleUiAction
import dev.galex.yamvil.models.simple.SimpleUiEvent
import dev.galex.yamvil.models.simple.SimpleUiState
import dev.galex.yamvil.viewmodels.SimpleMVIChannelViewModel

/**
 * A simple MVI dialog fragment.
 */
class SimpleMVIChannelDialogFragment: MVIChannelDialogFragment<SimpleUiState, SimpleUiEvent, SimpleUiAction>() {

    override val viewModel: SimpleMVIChannelViewModel by viewModels()

    override fun observeUiState(state: SimpleUiState) { /* NO-OP */ }

    override fun consumeAction(action: SimpleUiAction) { /* NO-OP */ }
}