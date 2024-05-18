package dev.galex.yamvil.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import dev.galex.yamvil.models.base.BaseUiState

@Composable
fun <UiAction, UiState : BaseUiState<UiAction>> LaunchedActionEffect(
    uiState: UiState,
    block: (UiAction) -> Unit
) {
    LaunchedEffect(uiState.action) {
        uiState.onAction(block)
    }
}