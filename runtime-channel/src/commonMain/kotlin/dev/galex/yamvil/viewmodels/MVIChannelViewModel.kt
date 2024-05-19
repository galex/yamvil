package dev.galex.yamvil.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class MVIChannelViewModel<UiState, UiEvent, UiAction>(
    protected val savedStateHandle: SavedStateHandle? = null,
) : ViewModel() {

    private val _uiState by lazy { MutableStateFlow(initializeUiState()) }
    val uiState get() = _uiState.asStateFlow()

    private val _uiAction by lazy { Channel<UiAction>() }
    val uiAction get() = _uiAction.receiveAsFlow()

    fun update(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    fun send(uiAction: UiAction) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            _uiAction.send(uiAction)
        }
    }

    abstract fun initializeUiState(): UiState
    abstract fun handleEvent(event: UiEvent)
}