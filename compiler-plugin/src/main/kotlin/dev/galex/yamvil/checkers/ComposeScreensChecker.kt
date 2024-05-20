package dev.galex.yamvil.checkers

import dev.galex.yamvil.dsl.YamvilConfiguration
import dev.galex.yamvil.dsl.YamvilLevel
import dev.galex.yamvil.errors.YamvilFirErrors.NO_VIEW_MODEL_ERROR
import dev.galex.yamvil.errors.YamvilFirErrors.NO_VIEW_MODEL_WARNING
import dev.galex.yamvil.models.BaseMviViewModelInfo
import dev.galex.yamvil.models.ParameterInfo
import dev.galex.yamvil.models.ParametersInfo
import dev.galex.yamvil.models.ViewModelInfo
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.diagnostics.reportOn
import org.jetbrains.kotlin.fir.analysis.checkers.MppCheckerKind
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFunctionChecker
import org.jetbrains.kotlin.fir.declarations.FirDeclaration
import org.jetbrains.kotlin.fir.declarations.FirFunction
import org.jetbrains.kotlin.fir.declarations.hasAnnotation
import org.jetbrains.kotlin.fir.declarations.utils.nameOrSpecialName
import org.jetbrains.kotlin.fir.resolve.providers.getRegularClassSymbolByClassId
import org.jetbrains.kotlin.fir.resolve.providers.symbolProvider
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol
import org.jetbrains.kotlin.fir.types.ConeKotlinType
import org.jetbrains.kotlin.fir.types.classId
import org.jetbrains.kotlin.fir.types.coneType
import org.jetbrains.kotlin.fir.types.type
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName

class ComposeScreensChecker(
    private val configuration: YamvilConfiguration
) : FirFunctionChecker(MppCheckerKind.Common) {

    override fun check(declaration: FirFunction, context: CheckerContext, reporter: DiagnosticReporter) {

        val diagnosticFactory = configuration.asLevel()
        val functionName = declaration.nameOrSpecialName.asString()

        // Stops checking a function if it not on par with our minimum requirements
        if (!declaration.isComposable(context) || !functionName.endsWith(configuration.screenSuffix)) return
        // Get ViewModel class
        val viewModelInfo = declaration.findViewModel(functionName, context)

        // Reports if we can't find a ViewModel class with the same prefix in the same package of this file
        if (viewModelInfo == null || viewModelInfo.exists == false) {
            reporter.reportOn(declaration.source, diagnosticFactory, "Could not find ViewModel class ${viewModelInfo?.fullName}", context)
            return
        }

        // Reports if the ViewModel doesn't implement MVIViewModel
        if (viewModelInfo.implementsBaseClass == false) {
            reporter.reportOn(declaration.source, diagnosticFactory, "${viewModelInfo.fqName.asString()} should implement MVIViewModel", context)
            return
        }

        // Check that we have parameters in our Composable for the uiState and for the uiEvent
        val uiStateInfo = declaration.getParametersInfo(viewModelInfo.uiStateClass, viewModelInfo.uiEventClass)

        // uiState verification
        if (uiStateInfo?.uiStateParameter?.exists == false ) {
            reporter.reportOn(declaration.source, diagnosticFactory, "$functionName should have a parameter for receiving the ViewModel's UiState class ${viewModelInfo.uiStateClass?.asString()}", context)
            return
        }

        if (uiStateInfo?.uiStateParameter?.name != configuration.uiStateParameterName) {
            reporter.reportOn(declaration.source, diagnosticFactory, "Please rename ${uiStateInfo?.uiStateParameter?.name} to \"${configuration.uiStateParameterName}\"", context)
        }

        // uiEvent verification
        if (uiStateInfo?.uiEventParameter?.exists == false ) {
            reporter.reportOn(declaration.source, diagnosticFactory, "$functionName should have a lambda parameter receiving ${viewModelInfo.uiEventClass?.asString()}", context)
            return
        }

        if (uiStateInfo?.uiEventParameter?.name != configuration.uiEventFunctionParameterMame) {
            reporter.reportOn(declaration.source, diagnosticFactory, "Please rename ${uiStateInfo?.uiEventParameter?.name} to handleEvent or onEvent", context)
        }
    }

    private fun FirFunction.findViewModel(functionName: String, context: CheckerContext): ViewModelInfo? {

        val packageName = context.containingFile?.packageDirective?.packageFqName ?: return null
        val viewModelName = FqName(functionName.replace("Screen", "ViewModel"))
        val viewModelClassId = ClassId(packageName, viewModelName, isLocal = false)

        val classSymbol: FirRegularClassSymbol? =
            context.session.symbolProvider.getRegularClassSymbolByClassId(viewModelClassId)

        val baseViewModelInfo: BaseMviViewModelInfo? = classSymbol?.findBaseSuperType()

        return ViewModelInfo(
            packageFqName = packageName,
            fqName = viewModelName,
            exists = classSymbol != null,
            implementsBaseClass = baseViewModelInfo?.implemented ?: false,
            uiStateClass = baseViewModelInfo?.uiStateClass,
            uiEventClass = baseViewModelInfo?.uiEventClass,
            uiActionClass = " "
        )
    }

    private fun FirRegularClassSymbol.findBaseSuperType(): BaseMviViewModelInfo {

        val foundClass: ConeKotlinType? = resolvedSuperTypes.find {
            it.classId == ClassId(FqName("dev.galex.yamvil.viewmodels"), FqName("MVIViewModel"), false)
        }

        return BaseMviViewModelInfo(
            implemented = foundClass != null,
            uiStateClass = foundClass?.typeArguments?.get(0)?.type?.classId?.asSingleFqName(),
            uiEventClass = foundClass?.typeArguments?.get(1)?.type?.classId?.asSingleFqName(),
        )
    }

    private fun FirDeclaration.isComposable(context: CheckerContext): Boolean {
        val packageName = FqName("androidx.compose.runtime")
        val name = FqName("Composable")
        val annotationClassId = ClassId(packageName, name, isLocal = false)
        return hasAnnotation(annotationClassId, context.session)
    }

    private fun YamvilConfiguration.asLevel() = when (level) {
        YamvilLevel.Warning -> NO_VIEW_MODEL_WARNING
        YamvilLevel.Error -> NO_VIEW_MODEL_ERROR
    }

    private fun FirFunction.getParametersInfo(uiStateClass: FqName?, uiEventClass: FqName?): ParametersInfo? {

        val uiStateParameter = valueParameters.find {
            it.returnTypeRef.coneType.classId?.asSingleFqName() == uiStateClass
        }

        val uiEventParameter = valueParameters.find {
            it.returnTypeRef.coneType.classId?.asSingleFqName()?.asString() == "kotlin.Function1"
                    && it.returnTypeRef.coneType.typeArguments.firstOrNull { it.type?.classId?.asString() == uiEventClass?.asString() } != null
        }

        return ParametersInfo(
            uiStateParameter = ParameterInfo(
                exists = uiStateParameter != null,
                name = uiStateParameter?.name?.asString()
            ),
            uiEventParameter = ParameterInfo(
                exists = uiEventParameter != null,
                name = uiEventParameter?.name?.asString()
            ),
        )
    }
}
