package dev.conguard.compiler.runners

import org.jetbrains.kotlin.test.builders.TestConfigurationBuilder
import org.jetbrains.kotlin.test.services.EnvironmentBasedStandardLibrariesPathProvider
import org.jetbrains.kotlin.test.services.KotlinStandardLibrariesPathProvider

abstract class AbstractDiagnosticTest : BaseTestRunner() {
  override fun TestConfigurationBuilder.configuration() {
    commonFirWithPluginFrontendConfiguration()
  }

  override fun createKotlinStandardLibrariesPathProvider(): KotlinStandardLibrariesPathProvider {
    return EnvironmentBasedStandardLibrariesPathProvider
  }
}
