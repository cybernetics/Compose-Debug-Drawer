package com.alorma.drawer_base

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
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
    module: DebugModule
) {
    val semanticModifier = Modifier.semantics {
        testTag = "Module header ${module.tag}"
    }
    Surface(
        modifier = semanticModifier + Modifier.fillMaxWidth().height(48.dp),
        color = DrawerColors.current.onSurface.compositeOverSurface(alpha = 0.12f),
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
fun DrawerModuleHeaderText(
    module: DebugModule
) {
    val semanticsModifier = Modifier.semantics {
        testTag = "Module header text ${module.tag}"
    }
    Text(
        modifier = semanticsModifier,
        text = module.title,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun DrawerModuleHeaderIcon(module: DebugModule, size: Dp) {

    val semanticsModifier = Modifier.semantics {
        testTag = "Module header icon ${module.tag}"
    }
    val modifier = semanticsModifier + Modifier.preferredSize(size = size)
    when (module.icon) {
        is IconType.Vector -> Icon(
            modifier = modifier,
            asset = vectorResource(
                id = module.icon.drawableRes
            ),
        )
        is IconType.Image -> Icon(
            modifier = modifier,
            asset = imageResource(
                id = module.icon.drawableRes
            ),
        )
    }
}