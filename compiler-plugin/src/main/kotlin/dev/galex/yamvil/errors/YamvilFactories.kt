package dev.galex.yamvil.errors

import dev.galex.yamvil.dsl.YamvilLevel
import org.jetbrains.kotlin.diagnostics.SourceElementPositioningStrategies
import org.jetbrains.kotlin.diagnostics.error1
import org.jetbrains.kotlin.diagnostics.warning1
import org.jetbrains.kotlin.psi.KtElement

object YamvilFactories {

    val NO_VIEW_MODEL_WARNING by warning1<KtElement, String>(SourceElementPositioningStrategies.DECLARATION_NAME)
    val NO_VIEW_MODEL_ERROR by error1<KtElement, String>(SourceElementPositioningStrategies.DECLARATION_NAME)

    val VIEW_MODEL_NO_MVI_WARNING by warning1<KtElement, String>(SourceElementPositioningStrategies.DECLARATION_NAME)
    val VIEW_MODEL_NO_MVI_ERROR by error1<KtElement, String>(SourceElementPositioningStrategies.DECLARATION_NAME)

    val NO_UI_STATE_PARAMETER_WARNING by warning1<KtElement, String>(SourceElementPositioningStrategies.DECLARATION_NAME)
    val NO_UI_STATE_PARAMETER_ERROR by error1<KtElement, String>(SourceElementPositioningStrategies.DECLARATION_NAME)

    val RENAME_UI_STATE_PARAMETER_WARNING by warning1<KtElement, String>(SourceElementPositioningStrategies.DECLARATION_NAME)
    val RENAME_UI_STATE_PARAMETER_ERROR by error1<KtElement, String>(SourceElementPositioningStrategies.DECLARATION_NAME)

    val NO_HANDLE_EVENT_PARAMETER_WARNING by warning1<KtElement, String>(SourceElementPositioningStrategies.DECLARATION_NAME)
    val NO_HANDLE_EVENT_PARAMETER_ERROR by error1<KtElement, String>(SourceElementPositioningStrategies.DECLARATION_NAME)

    val RENAME_HANDLE_EVENT_PARAMETER_WARNING by warning1<KtElement, String>(SourceElementPositioningStrategies.DECLARATION_NAME)
    val RENAME_HANDLE_EVENT_PARAMETER_ERROR by error1<KtElement, String>(SourceElementPositioningStrategies.DECLARATION_NAME)

    fun noViewModel(level: YamvilLevel) = when (level) {
        YamvilLevel.Warning -> NO_VIEW_MODEL_WARNING
        YamvilLevel.Error -> NO_VIEW_MODEL_ERROR
    }

    fun noMVIViewModel(level: YamvilLevel) = when (level) {
        YamvilLevel.Warning -> VIEW_MODEL_NO_MVI_WARNING
        YamvilLevel.Error -> VIEW_MODEL_NO_MVI_ERROR
    }

    fun noUiStateParameter(level: YamvilLevel) = when (level) {
        YamvilLevel.Warning -> NO_UI_STATE_PARAMETER_WARNING
        YamvilLevel.Error -> NO_UI_STATE_PARAMETER_ERROR
    }

    fun renameUiStateParameter(level: YamvilLevel) = when (level) {
        YamvilLevel.Warning -> RENAME_UI_STATE_PARAMETER_WARNING
        YamvilLevel.Error -> RENAME_UI_STATE_PARAMETER_ERROR
    }

    fun noHandleEventParameter(level: YamvilLevel) = when (level) {
        YamvilLevel.Warning -> NO_HANDLE_EVENT_PARAMETER_WARNING
        YamvilLevel.Error -> NO_HANDLE_EVENT_PARAMETER_ERROR
    }

    fun renameHandleEventParameter(level: YamvilLevel) = when (level) {
        YamvilLevel.Warning -> RENAME_HANDLE_EVENT_PARAMETER_WARNING
        YamvilLevel.Error -> RENAME_HANDLE_EVENT_PARAMETER_ERROR
    }
}
