package dev.galex.yamvil.sample.features.dashboard

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.galex.yamvil.compose.LaunchedActionEffect

@Composable
fun DashboardScreen(
    state: DashboardUiState,
    handleEvent: (DashboardUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedActionEffect(state) { action: DashboardUiAction ->
        when (action) {
            DashboardUiAction.NavigateToNext -> {}
        }
    }

    when (state.state) {
        is DashboardUiState.ContentState.Loading -> DashboardLoadingContent()
        is DashboardUiState.ContentState.Error -> DashboardErrorContent()
        is DashboardUiState.ContentState.Content -> DashboardContent(state.state)
    }

    Text(text = "DashboardScreen")
}

@Composable
fun DashboardLoadingContent(
    modifier: Modifier = Modifier
) {
    // Do something with loading
}

@Composable
fun DashboardErrorContent(
    modifier: Modifier = Modifier
) {
    // Do something with error
}

@Composable
fun DashboardContent(
    content: DashboardUiState.ContentState.Content,
    modifier: Modifier = Modifier
) {
    // Do something with content
}