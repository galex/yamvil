package dev.galex.yamvil.errors

import org.jetbrains.kotlin.diagnostics.SourceElementPositioningStrategies
import org.jetbrains.kotlin.diagnostics.error0
import org.jetbrains.kotlin.diagnostics.error1
import org.jetbrains.kotlin.diagnostics.rendering.RootDiagnosticRendererFactory
import org.jetbrains.kotlin.diagnostics.warning0
import org.jetbrains.kotlin.diagnostics.warning1
import org.jetbrains.kotlin.psi.KtElement

object YamvilFirErrors {

    val NO_VIEW_MODEL_WARNING by warning1<KtElement, String>(SourceElementPositioningStrategies.DECLARATION_NAME)
    val NO_VIEW_MODEL_ERROR by error1<KtElement, String>(SourceElementPositioningStrategies.DECLARATION_NAME)
}
