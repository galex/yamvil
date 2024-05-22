package dev.galex.yamvil.runners

import dev.conguard.compiler.services.ExtensionRegistrarConfigurator
import dev.conguard.compiler.services.PluginDslProvider
import org.jetbrains.kotlin.platform.jvm.JvmPlatforms
import org.jetbrains.kotlin.test.TargetBackend
import org.jetbrains.kotlin.test.builders.TestConfigurationBuilder
import org.jetbrains.kotlin.test.directives.FirDiagnosticsDirectives
import org.jetbrains.kotlin.test.directives.JvmEnvironmentConfigurationDirectives
import org.jetbrains.kotlin.test.initIdeaConfiguration
import org.jetbrains.kotlin.test.model.DependencyKind
import org.jetbrains.kotlin.test.runners.AbstractKotlinCompilerTest
import org.jetbrains.kotlin.test.runners.baseFirDiagnosticTestConfiguration
import org.jetbrains.kotlin.test.services.EnvironmentBasedStandardLibrariesPathProvider
import org.jetbrains.kotlin.test.services.KotlinStandardLibrariesPathProvider
import org.junit.jupiter.api.BeforeAll

abstract class BaseTestRunner : AbstractKotlinCompilerTest() {
  companion object {
    @BeforeAll
    @JvmStatic
    fun setUp() {
      initIdeaConfiguration()
    }
  }

  override fun createKotlinStandardLibrariesPathProvider(): KotlinStandardLibrariesPathProvider {
    return EnvironmentBasedStandardLibrariesPathProvider
  }
}

fun TestConfigurationBuilder.commonFirWithPluginFrontendConfiguration() {
  baseFirDiagnosticTestConfiguration()

  defaultDirectives {
    +FirDiagnosticsDirectives.ENABLE_PLUGIN_PHASES
    +FirDiagnosticsDirectives.FIR_DUMP
    +JvmEnvironmentConfigurationDirectives.FULL_JDK
  }

  useConfigurators(
    ::PluginDslProvider,
    ::ExtensionRegistrarConfigurator
  )
}
