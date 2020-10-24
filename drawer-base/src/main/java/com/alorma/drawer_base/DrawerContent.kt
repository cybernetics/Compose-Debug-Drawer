package com.alorma.drawer_base

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(
    drawerModules: @Composable () -> List<DebugModule> = { emptyList() },
    moduleModifier: Modifier? = null,
) {
    val items = drawerModules()
    LazyColumnForIndexed(
        modifier = Modifier.fillMaxSize(),
        items = items
    ) { index, module ->
        DrawerModule(
            module = module,
            modifier = moduleModifier,
        )
        if (index < items.size + 1) {
            Spacer(modifier = Modifier.preferredHeight(8.dp))
        }
    }
}