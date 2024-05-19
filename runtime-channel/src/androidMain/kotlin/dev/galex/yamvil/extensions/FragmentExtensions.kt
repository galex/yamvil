package dev.galex.yamvil.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <UiAction> Fragment.observeActionFlow(
    channel: Flow<UiAction>,
    observeUiAction: (UiAction) -> Unit,
) {
    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main.immediate) {
        repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
            channel.collect { action: UiAction ->
                observeUiAction(action)
            }
        }
    }
}