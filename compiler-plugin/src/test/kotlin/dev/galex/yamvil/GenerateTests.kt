package dev.galex.yamvil

import dev.conguard.compiler.runners.AbstractDiagnosticTest
import org.jetbrains.kotlin.generators.generateTestGroupSuiteWithJUnit5

fun main() {
  generateTestGroupSuiteWithJUnit5 {
    testGroup(testDataRoot = "src/test-data", testsRoot = "src/test-gen") {
      testClass<AbstractDiagnosticTest> { model("diagnostics") }
    }
  }
}
