package dev.galex.yamvil.models

import org.jetbrains.kotlin.name.FqName

data class BaseMviViewModelInfo(
    val implemented: Boolean,
    val uiStateClass: FqName?,
    val uiEventClass: FqName?,
)