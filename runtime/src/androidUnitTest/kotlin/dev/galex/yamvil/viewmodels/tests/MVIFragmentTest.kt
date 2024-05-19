package dev.galex.yamvil.viewmodels.tests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.viewModels
import com.google.common.truth.Truth.assertThat
import dev.galex.yamvil.fragments.base.MVIFragment
import dev.galex.yamvil.models.base.Consumable
import dev.galex.yamvil.viewmodels.models.TestedUiAction
import dev.galex.yamvil.viewmodels.models.TestedUiEvent
import dev.galex.yamvil.viewmodels.models.TestedUiState
import dev.galex.yamvil.viewmodels.models.TestedViewModel
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.apply
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
class MVIFragmentTest {

    class TestedFragment: MVIFragment<TestedUiState, TestedUiEvent>() {

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

            state.onAction {
                actionCounter++
            }
        }
    }

    @Test
    fun `Checking Initial State`() {
        val scenario = launchFragmentInContainer<TestedFragment>()
        scenario.onFragment { fragment ->
            assertThat(fragment.stateCounter).isEqualTo(1)
            assertThat(fragment.lastState).isEqualTo(TestedUiState())
        }
    }

    @Test
    fun `Triggering FirstEvent via handleEvent() - UiState is updated`() {
        val scenario = launchFragmentInContainer<TestedFragment>()
        scenario.onFragment { fragment ->
            assertThat(fragment.stateCounter).isEqualTo(1)
            fragment.viewModel.handleEvent(TestedUiEvent.FirstEvent)
            assertThat(fragment.stateCounter).isEqualTo(2)
            assertThat(fragment.lastState).isEqualTo(
                TestedUiState(
                    firstEventTriggered = true
                )
            )
        }
    }

    @Test
    fun `Triggering FirstEvent via send() - UiState is updated`() {
        val scenario = launchFragmentInContainer<TestedFragment>()
        scenario.onFragment { fragment ->
            fragment.apply {
                TestedUiEvent.FirstEvent.send()
            }
            assertThat(fragment.lastState).isEqualTo(
                TestedUiState(
                    action = null,
                    firstEventTriggered = true,
                    secondEventTriggered = false,
                )
            )
        }
    }

    @Test
    fun `Triggering FirstEvent and Second - UiState is updated`() {
        val scenario = launchFragmentInContainer<TestedFragment>()
        scenario.onFragment { fragment ->
            assertThat(fragment.stateCounter).isEqualTo(1)
            fragment.viewModel.handleEvent(TestedUiEvent.FirstEvent)
            assertThat(fragment.stateCounter).isEqualTo(2)
            fragment.viewModel.handleEvent(TestedUiEvent.SecondEvent)
            assertThat(fragment.stateCounter).isEqualTo(3)
            assertThat(fragment.lastState).isEqualTo(
                TestedUiState(
                    action = null,
                    firstEventTriggered = true,
                    secondEventTriggered = true,
                )
            )
        }
    }

    @Test
    fun `Triggering FirstEvent and Second - UiState is updated - Recreation`() {
        val scenario = launchFragmentInContainer<TestedFragment>()
        scenario.onFragment { fragment ->
            assertThat(fragment.stateCounter).isEqualTo(1)
            fragment.viewModel.handleEvent(TestedUiEvent.FirstEvent)
            assertThat(fragment.stateCounter).isEqualTo(2)
            fragment.viewModel.handleEvent(TestedUiEvent.SecondEvent)
            assertThat(fragment.stateCounter).isEqualTo(3)
            assertThat(fragment.lastState).isEqualTo(
                TestedUiState(
                    action = null,
                    firstEventTriggered = true,
                    secondEventTriggered = true,
                )
            )
        }

        scenario.recreate()

        scenario.onFragment { fragment ->
            assertThat(fragment.stateCounter).isEqualTo(1)
            assertThat(fragment.lastState).isEqualTo(
                TestedUiState(
                    action = null,
                    firstEventTriggered = true,
                    secondEventTriggered = true,
                )
            )
        }
    }

    @Test
    fun `Triggering ThirdEvent - UiState is updated with an Action`() {
        val scenario = launchFragmentInContainer<TestedFragment>()
        scenario.onFragment { fragment ->
            assertThat(fragment.stateCounter).isEqualTo(1)
            assertThat(fragment.actionCounter).isEqualTo(0)
            fragment.viewModel.handleEvent(TestedUiEvent.ThirdEvent)
            assertThat(fragment.stateCounter).isEqualTo(2)
            assertThat(fragment.actionCounter).isEqualTo(1)
            assertThat(fragment.lastState).isEqualTo(
                TestedUiState(
                    action = Consumable(TestedUiAction.FirstAction),
                    firstEventTriggered = false,
                    secondEventTriggered = false,
                )
            )
            assertThat(fragment.lastState?.action?.consumed).isTrue()
        }
    }

    @Test
    fun `Triggering ThirdEvent - UiState is updated with an Action used only once`() {
        val scenario = launchFragmentInContainer<TestedFragment>()
        scenario.onFragment { fragment ->
            assertThat(fragment.stateCounter).isEqualTo(1)
            assertThat(fragment.actionCounter).isEqualTo(0)
            fragment.viewModel.handleEvent(TestedUiEvent.ThirdEvent)
            assertThat(fragment.stateCounter).isEqualTo(2)
            assertThat(fragment.actionCounter).isEqualTo(1)
            assertThat(fragment.lastState).isEqualTo(
                TestedUiState(
                    action = Consumable(TestedUiAction.FirstAction),
                    firstEventTriggered = false,
                    secondEventTriggered = false,
                )
            )
            assertThat(fragment.lastState?.action?.consumed).isTrue()
        }

        scenario.recreate()

        scenario.onFragment { fragment ->
            assertThat(fragment.stateCounter).isEqualTo(1)
            assertThat(fragment.actionCounter).isEqualTo(0)
            assertThat(fragment.lastState).isEqualTo(
                TestedUiState(
                    action = Consumable(TestedUiAction.FirstAction),
                    firstEventTriggered = false,
                    secondEventTriggered = false,
                )
            )
            assertThat(fragment.lastState?.action?.consumed).isTrue()
        }
    }
}