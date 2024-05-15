package dev.conguard.compiler

import dev.conguard.compiler.runners.AbstractDiagnosticTest
import org.jetbrains.kotlin.generators.generateTestGroupSuiteWithJUnit5

fun main() {
  generateTestGroupSuiteWithJUnit5 {
    testGroup(testDataRoot = "compiler-plugin/src/test-data", testsRoot = "compiler-plugin/src/test-gen") {
      testClass<AbstractDiagnosticTest> { model("diagnostics") }
    }
  }
}
