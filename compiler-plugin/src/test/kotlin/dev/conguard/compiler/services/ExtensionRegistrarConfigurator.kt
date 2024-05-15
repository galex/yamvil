package dev.conguard.compiler.services

import dev.galex.yamvil.main.YamvilPluginRegistrar
import dev.galex.yamvil.dsl.YamvilConfiguration
import dev.galex.yamvil.dsl.YamvilLevel
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter
import org.jetbrains.kotlin.test.model.TestModule
import org.jetbrains.kotlin.test.services.EnvironmentConfigurator
import org.jetbrains.kotlin.test.services.TestServices

class ExtensionRegistrarConfigurator(
    testServices: TestServices,
) : EnvironmentConfigurator(testServices) {

    @OptIn(ExperimentalCompilerApi::class)
    override fun CompilerPluginRegistrar.ExtensionStorage.registerCompilerExtensions(
        module: TestModule,
        configuration: CompilerConfiguration
    ) {
        val yamvilConfiguration = YamvilConfiguration().apply {
            level = YamvilLevel.Warning
        }

        FirExtensionRegistrarAdapter.registerExtension(YamvilPluginRegistrar(yamvilConfiguration))
    }
}
