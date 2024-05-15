/*
 * Copyright (C) 2020 Brian Norman
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
import dev.galex.yamvil.BuildConfig
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey
import java.lang.IllegalArgumentException

@OptIn(ExperimentalCompilerApi::class)
@AutoService(CommandLineProcessor::class)
class YamvilCommandLineProcessor : CommandLineProcessor {
    companion object {
        private const val OPTION_JSON_CONFIG = "config"

        val ARG_JSON_CONVENTIONS = CompilerConfigurationKey<String>(OPTION_JSON_CONFIG)
    }

    override val pluginId: String = BuildConfig.KOTLIN_PLUGIN_ID

    override val pluginOptions: Collection<CliOption> = listOf(
        CliOption(
            optionName = OPTION_JSON_CONFIG,
            valueDescription = "string",
            description = "config",
            required = true,
        )
    )

    override fun processOption(
        option: AbstractCliOption,
        value: String,
        configuration: CompilerConfiguration
    ) {
        when (option.optionName) {
            OPTION_JSON_CONFIG -> configuration.put(ARG_JSON_CONVENTIONS, value)
            else -> throw IllegalArgumentException("unknown option passed to Yamvil")
        }
    }
}
