package dev.galex.yamvil.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dev.galex.yamvil.models.BaseUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class MVIViewModel<UiState : BaseUiState<*>, Event>(
    protected val savedStateHandle: SavedStateHandle? = null,
) : ViewModel() {

    private val _uiState by lazy { MutableStateFlow(initializeUiState()) }
    val uiState get() = _uiState.asStateFlow()

    fun update(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    abstract fun initializeUiState(): UiState
    abstract fun handleEvent(event: Event)
}