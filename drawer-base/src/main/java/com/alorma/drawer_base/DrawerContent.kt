package com.alorma.drawer_base

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DrawerContent(
    moduleModifier: Modifier? = null,
    initialModulesState: ModuleExpandedState = ModuleExpandedState.EXPANDED,
    drawerModules: @Composable () -> List<DebugModule> = { emptyList() },
) {
    val items = drawerModules()
    LazyColumnForIndexed(
        modifier = Modifier.fillMaxSize(),
        items = items
    ) { index, module ->
        DrawerModule(
            module = module,
            modifier = moduleModifier,
            initialModulesState = initialModulesState
        )
        if (index < items.size - 1) {
            Divider(color = DrawerColors.current.onSurface.compositeOverSurface(0.38f))
        }
    }
}