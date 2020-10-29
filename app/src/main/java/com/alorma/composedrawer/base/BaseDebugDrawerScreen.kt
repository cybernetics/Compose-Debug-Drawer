package com.alorma.composedrawer.base

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.alorma.composedrawer.BuildConfig
import com.alorma.composedrawer.modules.DemoActionsModule
import com.alorma.composedrawer.ui.ComposeDrawerTheme
import com.alorma.developer_shortcuts.ShortcutsModule
import com.alorma.drawer_base.DebugDrawerLayout
import com.alorma.drawer_base.ModuleExpandedState
import com.alorma.drawer_modules.BuildModule
import com.alorma.drawer_modules.DeviceModule
import com.alorma.navigation_module.NavigationModule

@Composable
fun DebugDrawerScreen(
    host: NavController,
    bodyContent: @Composable (DrawerState) -> Unit
) {
    ComposeDrawerTheme {
        DebugDrawerLayout(
            isDebug = { BuildConfig.DEBUG },
            initialDrawerState = DrawerValue.Open,
            initialModulesState = ModuleExpandedState.COLLAPSED,
            drawerModules = {
                listOf(
                    ShortcutsModule(),
                    NavigationModule(
                        navController = host,
                        stringParam = { route, paramName ->
                            "4321"
                        },
                        intParam = { route, paramName ->
                            221
                        }
                    ),
                    DemoActionsModule(),
                    BuildModule(),
                    DeviceModule(),
                )
            },
            bodyContent = bodyContent,
        )
    }
}