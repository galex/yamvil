/*
 * Copyright (C) 2024 Alexander Gherschon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.galex.yamvil.main

import com.google.auto.service.AutoService
import com.google.gson.Gson
import dev.galex.yamvil.dsl.YamvilConfiguration
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter
import java.util.*

@Suppress("unused")
@OptIn(ExperimentalCompilerApi::class)
@AutoService(CompilerPluginRegistrar::class)
class YamvilCompilerPluginRegistrar(
    private val defaultJsonYamvilConfiguration: String,
) : CompilerPluginRegistrar() {

    @Suppress("unused") // Used by service loader
    constructor() : this(
        defaultJsonYamvilConfiguration = Base64.getEncoder().encodeToString("{}".toByteArray()),
    )

    override val supportsK2: Boolean
        get() = true

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {

        val messageCollector = configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)
        messageCollector.report(CompilerMessageSeverity.INFO, "Running YamvilComponentRegistrar")
        val encodedConventions = configuration.get(YamvilCommandLineProcessor.ARG_JSON_CONVENTIONS, defaultJsonYamvilConfiguration)

        println("encoded config = $encodedConventions")
        val decodedConventions = String(Base64.getDecoder().decode(encodedConventions))
        println("decoded config = $decodedConventions")
        messageCollector.report(CompilerMessageSeverity.INFO, "Argument 'json conventions' = $decodedConventions")
        val yamvilConfiguration: YamvilConfiguration = Gson().fromJson(decodedConventions, YamvilConfiguration::class.java)
        println("yamvilConfiguration = $yamvilConfiguration")

        FirExtensionRegistrarAdapter.registerExtension(YamvilPluginRegistrar(yamvilConfiguration))
    }
}


