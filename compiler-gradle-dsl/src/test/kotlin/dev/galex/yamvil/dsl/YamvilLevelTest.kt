package dev.galex.yamvil.dsl

import com.google.common.truth.Truth
import org.junit.Test

class YamvilLevelTest {

    @Test
    fun `YamvilConfiguration - Default value is Error`() {
        // When
        val config = YamvilConfiguration()
        // Then
        Truth.assertThat(config.level).isEqualTo(YamvilLevel.Error)
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
}