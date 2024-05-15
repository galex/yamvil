package dev.galex.yamvil.sample.features.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.galex.yamvil.compose.LaunchedActionEffect

@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    handleEvent: (DashboardUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedActionEffect(uiState) { action: DashboardUiAction ->
        when (action) {
            DashboardUiAction.NavigateToNext -> {}
        }
    }

    when (uiState.state) {
        is DashboardUiState.ContentState.Loading -> DashboardLoadingContent()
        is DashboardUiState.ContentState.Error -> DashboardErrorContent()
        is DashboardUiState.ContentState.Content -> DashboardContent(uiState.state)
        else -> {}
    }
}

@Composable
fun DashboardLoadingContent(
    modifier: Modifier = Modifier
) {

}

@Composable
fun DashboardErrorContent(
    modifier: Modifier = Modifier
) {

}

@Composable
fun DashboardContent(
    content: DashboardUiState.ContentState.Content,
    modifier: Modifier = Modifier
) {

}