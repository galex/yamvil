package dev.galex.yamvil.checkers

import dev.galex.yamvil.dsl.YamvilConfiguration
import dev.galex.yamvil.dsl.YamvilLevel
import dev.galex.yamvil.errors.YamvilFirErrors.NO_VIEW_MODEL_ERROR
import dev.galex.yamvil.errors.YamvilFirErrors.NO_VIEW_MODEL_WARNING
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.diagnostics.reportOn
import org.jetbrains.kotlin.fir.analysis.checkers.MppCheckerKind
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFunctionChecker
import org.jetbrains.kotlin.fir.declarations.FirFunction
import org.jetbrains.kotlin.fir.declarations.utils.nameOrSpecialName

class YamvilFunctionsChecker(
    private val configuration: YamvilConfiguration
) : FirFunctionChecker(MppCheckerKind.Common) {

    override fun check(declaration: FirFunction, context: CheckerContext, reporter: DiagnosticReporter) {

        val functionName = declaration.nameOrSpecialName.asString()

        if (functionName.endsWith("Screen")) {

            val (factory, message) = when (configuration.level) {
                YamvilLevel.Warning -> NO_VIEW_MODEL_WARNING to "Where is your viewModel?"
                YamvilLevel.Error -> NO_VIEW_MODEL_ERROR to "Where is your viewModel?"
            }

            reporter.reportOn(declaration.source, factory, message, context)
        }
    }
}