package com.alorma.drawer_base

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable

interface DebugModule {

    val icon: IconType

    val title: String

    @Composable
    fun build()
}

sealed class IconType {
    @get: DrawableRes
    abstract val drawableRes: Int

    data class Vector(override val drawableRes: Int) : IconType()
    data class Image(override val drawableRes: Int) : IconType()
}