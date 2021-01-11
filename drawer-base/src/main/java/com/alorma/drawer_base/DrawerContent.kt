package com.alorma.drawer_base

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    initialModulesState: ModuleExpandedState = ModuleExpandedState.EXPANDED,
    drawerModules: @Composable () -> List<DebugModule> = { emptyList() },
) {
    val items = drawerModules()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(items = items,
            itemContent = { index, module ->
                DrawerModule(
                    module = module,
                    modifier = modifier,
                    initialModulesState = initialModulesState
                )
                if (index < items.size - 1) {
                    Divider(color = DrawerColors.current.onSurface.compositeOverSurface(0.30f))
                }
            })
    }
}