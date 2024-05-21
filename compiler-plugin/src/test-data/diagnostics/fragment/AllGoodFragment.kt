import androidx.fragment.app.viewModels
import dev.galex.yamvil.fragments.base.MVIFragment
import dev.galex.yamvil.models.base.Consumable
import dev.galex.yamvil.viewmodels.MVIViewModel

sealed class DashboardUiAction {
    data object NavigateToNext : DashboardUiAction()
}

sealed interface DashboardUiEvent {
    data object ClickOnNext: DashboardUiEvent
}

data class DashboardUiState(
    override val action: Consumable<DashboardUiAction>? = null,
    val state: ContentState,
) : Actionable<DashboardUiAction> {

    sealed interface ContentState {
        data object Loading : ContentState
        data object Error : ContentState
        data class Content(
            val title: String,
            val description: String
        ) : ContentState
    }
}

class DashboardViewModel: MVIViewModel<DashboardUiState, DashboardUiEvent>() {

    override fun initializeUiState(): DashboardUiState {
        return DashboardUiState(state = DashboardUiState.ContentState.Loading)
    }
    override fun handleEvent(event: DashboardUiEvent) {
        when (event) {
            is DashboardUiEvent.ClickOnNext -> onClickOnNext()
        }
    }

    private fun onClickOnNext() {
        update { copy(action = Consumable(DashboardUiAction.NavigateToNext)) }
    }
}

class AllGoodFragment: MVIFragment<DashboardUiState, DashboardUiEvent>() {

    override val viewModel: DashboardViewModel by viewModels()
    override fun observeUiState(uiState: DashboardUiState) {
        when(uiState.state) {
            DashboardUiState.ContentState.Loading -> onLoading()
            is DashboardUiState.ContentState.Content -> onContent(uiState.state)
            DashboardUiState.ContentState.Error -> onError()
        }

        uiState.onAction { action ->
            when(action) {
                DashboardUiAction.NavigateToNext -> navigateToNext()
            }
        }
    }

    private fun navigateToNext() {
        // Do something with navigateToNext
    }

    private fun onLoading() {
        // Do something with loading
    }

    private fun onContent(content: DashboardUiState.ContentState.Content) {
        // Do something with content
    }

    private fun onError() {
        // Do something with error
    }
}