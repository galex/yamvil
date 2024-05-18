package dev.galex.yamvil.models

import org.jetbrains.kotlin.name.FqName

data class ViewModelInfo(
    val packageFqName: FqName,
    val fqName: FqName,
    val exists: Boolean,
    val implementsBaseClass: Boolean,
    val uiStateClass: FqName?,
    val uiEventClass: FqName?,
    val uiActionClass: String,
) {
    val fullName
        get() = "$packageFqName.$fqName"
}