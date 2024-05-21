package dev.conguard.compiler.services

import org.jetbrains.kotlin.cli.jvm.config.addJvmClasspathRoot
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.test.model.TestModule
import org.jetbrains.kotlin.test.services.EnvironmentConfigurator
import org.jetbrains.kotlin.test.services.TestServices
import org.jetbrains.kotlin.test.services.assertions
import java.io.File
import java.io.FilenameFilter

class PluginDslProvider(testServices: TestServices) : EnvironmentConfigurator(testServices) {
    companion object {
        private const val DSL_JAR_DIR = "../compiler-gradle-dsl/build/libs/"
        private val DSL_JAR_FILTER = FilenameFilter { _, name -> name.startsWith("compiler-gradle-dsl") && name.endsWith(".jar") }
    }

    override fun configureCompilerConfiguration(configuration: CompilerConfiguration, module: TestModule) {
        val libDir = File(DSL_JAR_DIR)
        testServices.assertions.assertTrue(libDir.exists() && libDir.isDirectory, failMessage)
        val jar = libDir.listFiles(DSL_JAR_FILTER)?.firstOrNull() ?: testServices.assertions.fail(failMessage)
        configuration.addJvmClasspathRoot(jar)
    }

    private val failMessage = { "Jar with DSL does not exist. Please run ./gradlew :compiler-gradle-dsl:jar" }
}
