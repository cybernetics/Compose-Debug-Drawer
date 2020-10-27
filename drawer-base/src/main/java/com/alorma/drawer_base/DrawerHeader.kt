package com.alorma.drawer_base

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DrawerModuleHeader(
    module: DebugModule,
    onClick: () -> Unit
) {
    val semanticModifier = Modifier
        .clickable(onClick = onClick)
        .semantics {
            testTag = "Module header ${module.tag}"
        }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp) + semanticModifier,
        color = DrawerColors.current.surface,
        contentColor = DrawerColors.current.secondary,
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DrawerModuleHeaderIcon(module, 32.dp)
            Spacer(modifier = Modifier.preferredWidth(8.dp))
            DrawerModuleHeaderText(module)
        }
    }
}

@Composable
fun DrawerModuleHeaderIcon(module: DebugModule, size: Dp) {

    val semanticsModifier = Modifier.semantics {
        testTag = "Module header icon ${module.tag}"
    }
    val modifier = semanticsModifier + Modifier.preferredSize(size = size)
    when (module.icon) {
        is IconType.Vector -> Icon(
            tint = DrawerColors.current.onSurface,
            modifier = modifier,
            asset = vectorResource(
                id = module.icon.drawableRes
            ),
        )
        is IconType.Image -> Icon(
            tint = DrawerColors.current.onSurface,
            modifier = modifier,
            asset = imageResource(
                id = module.icon.drawableRes
            ),
        )
    }
}

@Composable
fun DrawerModuleHeaderText(
    module: DebugModule
) {
    val semanticsModifier = Modifier.semantics {
        testTag = "Module header text ${module.tag}"
    }
    Text(
        color = DrawerColors.current.primary,
        modifier = semanticsModifier,
        text = module.title,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Bold,
    )
}
