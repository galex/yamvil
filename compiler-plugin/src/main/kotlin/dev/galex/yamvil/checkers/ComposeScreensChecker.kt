package dev.galex.yamvil.checkers

import dev.galex.yamvil.dsl.YamvilConfiguration
import dev.galex.yamvil.errors.YamvilFactories
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
import org.jetbrains.kotlin.fir.types.FirTypeRef
import org.jetbrains.kotlin.fir.types.classId
import org.jetbrains.kotlin.fir.types.coneType
import org.jetbrains.kotlin.fir.types.type
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName

class ComposeScreensChecker(
    private val config: YamvilConfiguration
) : FirFunctionChecker(MppCheckerKind.Common) {

    override fun check(declaration: FirFunction, context: CheckerContext, reporter: DiagnosticReporter) {

        val functionName = declaration.nameOrSpecialName.asString()

        // Stops checking a function if it not on par with our minimum requirements
        if (!declaration.isComposable(context) || !functionName.endsWith(config.compose.screenSuffix)) return
        // Get ViewModel class
        val viewModelInfo = declaration.findViewModel(functionName, context)

        // Reports if we can't find a ViewModel class with the same prefix in the same package of this file
        if (viewModelInfo == null || viewModelInfo.exists == false) {
            reporter.reportOn(
                source = declaration.source,
                factory = YamvilFactories.noViewModel(config.level),
                a = "Could not find a ViewModel class at ${viewModelInfo?.fullName}",
                context = context,
            )
            return
        }

        // Reports if the ViewModel doesn't implement MVIViewModel
        if (viewModelInfo.implementsBaseClass == false) {
            reporter.reportOn(
                source = declaration.source,
                factory = YamvilFactories.noMVIViewModel(config.level),
                a = "${viewModelInfo.fullName} should implement dev.galex.yamvil.viewmodels.MVIViewModel",
                context = context,
            )
            return
        }

        // Check that we have parameters in our Composable for the uiState and for the uiEvent
        val uiStateInfo = declaration.getParametersInfo(viewModelInfo.uiStateClass, viewModelInfo.uiEventClass)

        // uiState existence verification
        if (uiStateInfo?.uiStateParameter?.exists == false ) {
            reporter.reportOn(
                source = declaration.source,
                factory = YamvilFactories.noUiStateParameter(config.level),
                a = "$functionName should have a parameter for receiving the ViewModel's UiState class ${viewModelInfo.uiStateClass?.asString()}",
                context = context,
            )
            return
        }

        // uiState parameter name
        if (uiStateInfo?.uiStateParameter?.name != config.compose.uiStateParameterName) {
            reporter.reportOn(
                source = declaration.source,
                factory = YamvilFactories.renameUiStateParameter(config.level),
                a = "Please rename parameter \"${uiStateInfo?.uiStateParameter?.name}\" to \"${config.compose.uiStateParameterName}\"",
                context = context,
            )
        }

        // handleEvent existence verification
        if (uiStateInfo?.uiEventParameter?.exists == false ) {
            reporter.reportOn(
                source = declaration.source,
                factory = YamvilFactories.noHandleEventParameter(config.level),
                a = "$functionName should have a lambda parameter receiving ${viewModelInfo.uiEventClass?.asString()}",
                context = context,
            )
            return
        }

        // handleEvent parameter name
        if (uiStateInfo?.uiEventParameter?.name != config.compose.handleEventParameterMame) {
            reporter.reportOn(
                source = declaration.source,
                factory = YamvilFactories.renameHandleEventParameter(config.level),
                a = "Please rename \"${uiStateInfo?.uiEventParameter?.name}\" to \"${config.compose.handleEventParameterMame}\"",
                context = context
            )
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
            implementsBaseClass = baseViewModelInfo?.implemented == true,
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

    private fun FirFunction.getParametersInfo(uiStateClass: FqName?, uiEventClass: FqName?): ParametersInfo? {

        val uiStateParameter = valueParameters.find {
            it.returnTypeRef.coneType.classId?.asSingleFqName() == uiStateClass
        }

        val uiEventParameter = valueParameters.find {
            it.returnTypeRef.isLambda() && it.returnTypeRef.hasParameter(uiEventClass)
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

    private fun FirTypeRef.isLambda(): Boolean {
        return coneType.classId?.asSingleFqName()?.asString() == "kotlin.Function1"
    }

    private fun FirTypeRef.hasParameter(ofType: FqName?): Boolean {
        return coneType.typeArguments.firstOrNull { it.type?.classId?.asString()?.replace("/", ".") == ofType?.asString() } != null
    }
}
