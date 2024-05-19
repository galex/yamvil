package dev.galex.yamvil.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.galex.yamvil.models.base.Actionable
import dev.galex.yamvil.models.base.Consumable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog

@RunWith(AndroidJUnit4::class)
class LaunchedActionEffectTest {

    @get:Rule val composeTestRule = createComposeRule()

    interface TestedUiAction {
        data object FirstAction: TestedUiAction
    }

    @Test
    fun `Launched Action Effect with a null action`() {

        val uiState = object: Actionable<TestedUiAction> {
            override val action: Consumable<TestedUiAction>? get() = null
        }

        composeTestRule.setContent {

            val counter = remember { mutableStateOf(0) }
            LaunchedActionEffect(uiState) { action ->
                when (action) {
                    TestedUiAction.FirstAction -> counter.value = counter.value + 1
                }
            }

            Text(text = "Counter = ${counter.value}")
        }

        composeTestRule.onNodeWithText("Counter = 0").assertIsDisplayed()
    }

    @Test
    fun `Launched Action Effect with an action`() {

        val uiState = object: Actionable<TestedUiAction> {
            override val action: Consumable<TestedUiAction>? get() = Consumable(TestedUiAction.FirstAction)
        }

        composeTestRule.setContent {

            val counter = remember { mutableStateOf(0) }
            LaunchedActionEffect(uiState) { action ->
                when (action) {
                    TestedUiAction.FirstAction -> counter.value = counter.value + 1
                }
            }

            Text(text = "Counter = ${counter.value}")
        }

        composeTestRule.onNodeWithText("Counter = 1").assertIsDisplayed()
    }
}