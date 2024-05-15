package dev.galex.yamvil.main

import dev.galex.yamvil.dsl.YamvilConfiguration
import dev.galex.yamvil.extensions.YamvilCheckersExtension
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar

class YamvilPluginRegistrar(private val configuration: YamvilConfiguration) : FirExtensionRegistrar() {
    override fun ExtensionRegistrarContext.configurePlugin() {
        +{ session: FirSession -> YamvilCheckersExtension(session, configuration) }
    }
}