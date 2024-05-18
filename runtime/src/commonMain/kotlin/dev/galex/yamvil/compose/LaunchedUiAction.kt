package dev.galex.yamvil.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import dev.galex.yamvil.models.base.Actionable

@Composable
fun <UiAction, UiState : Actionable<UiAction>> LaunchedActionEffect(
    uiState: UiState,
    block: (UiAction) -> Unit
) {
    LaunchedEffect(uiState.action) {
        uiState.onAction(block)
    }
}