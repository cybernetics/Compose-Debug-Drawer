package com.alorma.drawer_base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp

@Composable
fun DrawerModule(
    module: DebugModule,
    modifier: Modifier? = null,
) {
    val semanticsModifier = Modifier.semantics {
        testTag = "Module ${module.tag}"
    }
    val moduleModifier = modifier ?: Modifier

    Column(
        modifier = semanticsModifier + Modifier.fillMaxWidth() + moduleModifier,
    ) {
        DrawerModuleHeader(
            module = module,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = DrawerColors.current.onSurface.compositeOverSurface(alpha = 0.08f)
                ).padding(8.dp),
        ) {
            module.build()
        }
    }
}