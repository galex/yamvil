package dev.galex.yamvil.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import dev.galex.yamvil.models.BaseUiState

@Composable
fun <UiAction, UiState : BaseUiState<UiAction>> LaunchedActionEffect(
    uiState: UiState,
    block: (UiAction) -> Unit
) {
    LaunchedEffect(uiState.action) {
        uiState.action?.consume()?.let { action: UiAction ->
            block(action)
        }
    }
}