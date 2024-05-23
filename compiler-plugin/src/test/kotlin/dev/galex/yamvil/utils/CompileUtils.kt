package dev.galex.yamvil.utils

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import dev.galex.yamvil.main.YamvilCompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@OptIn(ExperimentalCompilerApi::class)
fun compile(
    plugin: CompilerPluginRegistrar = YamvilCompilerPluginRegistrar(),
    sourceFiles: List<SourceFile>,
): KotlinCompilation.Result {
    return KotlinCompilation().apply {
        sources = sourceFiles
        useIR = true
        compilerPluginRegistrars = listOf(plugin)
        inheritClassPath = true
    }.compile()
}

@OptIn(ExperimentalCompilerApi::class)
fun compile(
    plugin: CompilerPluginRegistrar = YamvilCompilerPluginRegistrar(),
    sourceFile: SourceFile,
): KotlinCompilation.Result {
    return compile(plugin, listOf(sourceFile))
}
