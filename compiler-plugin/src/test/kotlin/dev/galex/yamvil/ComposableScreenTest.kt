package dev.galex.yamvil

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import dev.galex.yamvil.dsl.YamvilConfiguration
import dev.galex.yamvil.utils.compile
import junit.framework.TestCase.assertEquals
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

import org.junit.Test
import org.junit.runner.RunWith

class ComposableScreenTest {

    @OptIn(ExperimentalCompilerApi::class)
    @Test
    fun `ComposableisOK`() {

        //val yamvilConfiguration = YamvilConfiguration()

//        val result = compile(
//            sourceFile = SourceFile.kotlin(
//                "SomeClass.kt",
//                """
//val test = 1
//                """
//            )
//        )

        //assertEquals(KotlinCompilation.ExitCode.COMPILATION_ERROR, result.exitCode)
    }
}