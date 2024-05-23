package dev.galex.yamvil.dsl

import com.google.common.truth.Truth
import org.junit.Test

class YamvilLevelTest {

    @Test
    fun `YamvilConfiguration - Default values`() {
        // When
        val config = YamvilConfiguration()
        // Then
        Truth.assertThat(config.level).isEqualTo(YamvilLevel.Error)
        Truth.assertThat(config.compose.screenSuffix).isEqualTo("Screen")
        Truth.assertThat(config.compose.uiStateParameterName).isEqualTo("uiState")
        Truth.assertThat(config.compose.handleEventParameterMame).isEqualTo("handleEvent")
    }

    @Test
    fun `YamvilConfiguration - Setting up Warning Level returns Warning level`() {
        // When
        val config = YamvilConfiguration().apply {
            level = YamvilLevel.Warning
        }
        // Then
        Truth.assertThat(config.level).isEqualTo(YamvilLevel.Warning)
    }

    @Test
    fun `YamvilConfiguration - Setting up Error Level returns Error level`() {
        // When
        val config = YamvilConfiguration().apply {
            level = YamvilLevel.Error
        }
        // Then
        Truth.assertThat(config.level).isEqualTo(YamvilLevel.Error)
    }

    @Test
    fun `YamvilConfiguration - Compose DSL`() {
        // When
        val config = YamvilConfiguration().apply {
            level = YamvilLevel.Error
            compose {
                screenSuffix = "MVIScreen"
                uiStateParameterName = "state"
                handleEventParameterMame = "onEvent"
            }
        }
        // Then
        Truth.assertThat(config.level).isEqualTo(YamvilLevel.Error)
        Truth.assertThat(config.compose.screenSuffix).isEqualTo("MVIScreen")
        Truth.assertThat(config.compose.uiStateParameterName).isEqualTo("state")
        Truth.assertThat(config.compose.handleEventParameterMame).isEqualTo("onEvent")
    }
}