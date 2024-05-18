package dev.galex.yamvil.viewmodels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.viewModels
import com.google.common.truth.Truth.assertThat
import dev.galex.yamvil.fragments.base.MVIFragment
import dev.galex.yamvil.models.base.BaseUiState
import dev.galex.yamvil.models.base.Consumable
import dev.galex.yamvil.viewmodels.MVIFragmentTest.TestedUiState
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
class MVIFragmentTest {

    sealed interface TestedUiAction {
        object FirstAction: TestedUiAction
    }

    sealed interface TestedUiEvent {
        object FirstEvent: TestedUiEvent
        object SecondEvent: TestedUiEvent
        object ThirdEvent: TestedUiEvent
    }

    data class TestedUiState(
        override val action: Consumable<TestedUiAction>? = null,
        var firstEventTriggered: Boolean = false,
        var secondEventTriggered: Boolean = false,
    ): BaseUiState<TestedUiAction>

    class TestedViewModel: MVIViewModel<TestedUiState, TestedUiEvent>() {

        override fun initializeUiState() = TestedUiState()

        override fun handleEvent(event: TestedUiEvent) {
            when (event) {
                is TestedUiEvent.FirstEvent -> update { copy(firstEventTriggered = true) }
                is TestedUiEvent.SecondEvent -> update { copy(secondEventTriggered = true) }
                is TestedUiEvent.ThirdEvent -> update { copy(action = Consumable(TestedUiAction.FirstAction)) }
            }
        }
    }

    class TestedFragment: MVIFragment<TestedUiState, TestedUiEvent, TestedUiAction>() {

        var stateCounter = 0
        var lastState: TestedUiState? = null
        var actionCounter = 0

        override val viewModel: TestedViewModel by viewModels()

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return View(requireContext())
        }

        override fun observeUiState(state: TestedUiState) {
            stateCounter++
            lastState = state
        }

        override fun consumeAction(action: TestedUiAction) {
            actionCounter++
        }
    }

    @Test
    fun `Checking Initial State`() = runTest {
        val scenario = launchFragmentInContainer<TestedFragment>()
        scenario.onFragment { fragment ->
            assertThat(fragment.stateCounter).isEqualTo(1)
            assertThat(fragment.lastState).isEqualTo(TestedUiState())
        }
    }

    @Test
    fun `Triggering FirstEvent via handleEvent() - UiState is updated`() = runTest {
        val scenario = launchFragmentInContainer<TestedFragment>()
        scenario.onFragment { fragment ->
            assertThat(fragment.stateCounter).isEqualTo(1)
            fragment.viewModel.handleEvent(TestedUiEvent.FirstEvent)
            assertThat(fragment.stateCounter).isEqualTo(2)
            assertThat(fragment.lastState).isEqualTo(TestedUiState(
                firstEventTriggered = true
            ))
        }
    }

    @Test
    fun `Triggering FirstEvent via send() - UiState is updated`() = runTest {
        val scenario = launchFragmentInContainer<TestedFragment>()
        scenario.onFragment { fragment ->
            fragment.apply {
                TestedUiEvent.FirstEvent.send()
            }
            assertThat(fragment.lastState).isEqualTo(TestedUiState(
                action = null,
                firstEventTriggered = true,
                secondEventTriggered = false,
            ))
        }
    }

    @Test
    fun `Triggering FirstEvent and Second - UiState is updated`() = runTest {
        val scenario = launchFragmentInContainer<TestedFragment>()
        scenario.onFragment { fragment ->
            assertThat(fragment.stateCounter).isEqualTo(1)
            fragment.viewModel.handleEvent(TestedUiEvent.FirstEvent)
            assertThat(fragment.stateCounter).isEqualTo(2)
            fragment.viewModel.handleEvent(TestedUiEvent.SecondEvent)
            assertThat(fragment.stateCounter).isEqualTo(3)
            assertThat(fragment.lastState).isEqualTo(TestedUiState(
                action = null,
                firstEventTriggered = true,
                secondEventTriggered = true,
            ))
        }
    }

    @Test
    fun `Triggering FirstEvent and Second - UiState is updated - Recreation`() = runTest {
        val scenario = launchFragmentInContainer<TestedFragment>()
        scenario.onFragment { fragment ->
            assertThat(fragment.stateCounter).isEqualTo(1)
            fragment.viewModel.handleEvent(TestedUiEvent.FirstEvent)
            assertThat(fragment.stateCounter).isEqualTo(2)
            fragment.viewModel.handleEvent(TestedUiEvent.SecondEvent)
            assertThat(fragment.stateCounter).isEqualTo(3)
            assertThat(fragment.lastState).isEqualTo(TestedUiState(
                action = null,
                firstEventTriggered = true,
                secondEventTriggered = true,
            ))
        }

        scenario.recreate()
        scenario.onFragment { fragment ->
            assertThat(fragment.stateCounter).isEqualTo(1)
            assertThat(fragment.lastState).isEqualTo(TestedUiState(
                action = null,
                firstEventTriggered = true,
                secondEventTriggered = true,
            ))
        }
    }
}