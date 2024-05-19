package dev.galex.yamvil.fragments.simple

import androidx.fragment.app.viewModels
import dev.galex.yamvil.fragments.base.MVIChannelFragment
import dev.galex.yamvil.models.simple.SimpleUiAction
import dev.galex.yamvil.models.simple.SimpleUiEvent
import dev.galex.yamvil.models.simple.SimpleUiState
import dev.galex.yamvil.viewmodels.SimpleMVIChannelViewModel

/**
 * A simple MVI fragment.
 */
class SimpleMVIChannelFragment: MVIChannelFragment<SimpleUiState, SimpleUiEvent, SimpleUiAction>() {

    override val viewModel: SimpleMVIChannelViewModel by viewModels()

    override fun observeUiState(state: SimpleUiState) { /* NO-OP */ }

    override fun consumeAction(action: SimpleUiAction) { /* NO-OP */ }
}