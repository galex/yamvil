package dev.galex.yamvil.fragments.simple

import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import dev.galex.yamvil.fragments.base.MVIFragment
import dev.galex.yamvil.models.simple.SimpleUiAction
import dev.galex.yamvil.models.simple.SimpleUiEvent
import dev.galex.yamvil.models.simple.SimpleUiState
import dev.galex.yamvil.viewmodels.SimpleMVIViewModel

/**
 * A simple MVI fragment.
 */
class SimpleMVIFragment: MVIFragment<SimpleUiState, SimpleUiEvent, SimpleUiAction>() {

    override val viewModel: SimpleMVIViewModel by viewModels()
}