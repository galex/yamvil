/*
 * Copyright (C) 2020 Alexander Gherschon
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

package dev.galex.yamvil.gradle

import com.google.gson.Gson
import dev.galex.yamvil.BuildConfig
import dev.galex.yamvil.dsl.YamvilConfiguration
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption
import java.util.*

@Suppress("unused")
class YamvilGradlePlugin : KotlinCompilerPluginSupportPlugin {
  override fun apply(target: Project): Unit = with(target) {
    extensions.create("yamvil", YamvilConfiguration::class.java)
  }

  override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean = true

  override fun getCompilerPluginId(): String = BuildConfig.KOTLIN_PLUGIN_ID

  override fun getPluginArtifact(): SubpluginArtifact = SubpluginArtifact(
    groupId = BuildConfig.KOTLIN_PLUGIN_GROUP,
    artifactId = BuildConfig.KOTLIN_COMPILER_PLUGIN_NAME,
    version = BuildConfig.KOTLIN_PLUGIN_VERSION
  )

  override fun applyToCompilation(
    kotlinCompilation: KotlinCompilation<*>
  ): Provider<List<SubpluginOption>> {

    val project = kotlinCompilation.target.project
    val extension = project.extensions.getByType(YamvilConfiguration::class.java)
    val encodedConfig = Base64.getEncoder().encodeToString(Gson().toJson(extension).toByteArray())

    return project.provider {
      listOf(
        SubpluginOption(key = "config", value = encodedConfig),
      )
    }
  }
}
