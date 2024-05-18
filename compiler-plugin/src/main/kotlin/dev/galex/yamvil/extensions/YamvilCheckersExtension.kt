package dev.galex.yamvil.extensions

import dev.galex.yamvil.checkers.ScreensChecker
import dev.galex.yamvil.dsl.YamvilConfiguration
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFunctionChecker
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension

class YamvilCheckersExtension(session: FirSession, private val configuration: YamvilConfiguration) :
    FirAdditionalCheckersExtension(session) {
    override val declarationCheckers: DeclarationCheckers =
        object : DeclarationCheckers() {

            override val functionCheckers: Set<FirFunctionChecker>
                get() = setOf(
                    ScreensChecker(configuration)
                )
        }
}