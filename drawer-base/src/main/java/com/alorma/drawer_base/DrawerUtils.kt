package com.alorma.drawer_base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ambientOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val DrawerColors = ambientOf { drawerColorsPalette }

@Composable
fun Color.compositeOverSurface(alpha: Float = 1f): Color {
    return copy(alpha = alpha).compositeOver(DrawerColors.current.surface)
}