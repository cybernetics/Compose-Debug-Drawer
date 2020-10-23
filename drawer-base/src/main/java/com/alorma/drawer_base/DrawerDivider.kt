package com.alorma.drawer_base

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerDivider() {
    Spacer(modifier = Modifier.preferredHeight(4.dp))
    Divider(
        color = DrawerColors.current.onSurface.compositeOverSurface(0.12f)
    )
    Spacer(modifier = Modifier.preferredHeight(4.dp))
}