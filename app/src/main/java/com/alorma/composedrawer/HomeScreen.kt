package com.alorma.composedrawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.alorma.composedrawer.modules.DemoActionsModule
import com.alorma.composedrawer.ui.ComposeDrawerTheme
import com.alorma.developer_shortcuts.ShortcutsModule
import com.alorma.drawer_base.DebugDrawerLayout
import com.alorma.drawer_base.ModuleExpandedState
import com.alorma.drawer_modules.BuildModule
import com.alorma.drawer_modules.DeviceModule

@Composable
fun HomeScreen() {
    DebugDrawerLayout(
        isDebug = { BuildConfig.DEBUG },
        initialDrawerState = DrawerValue.Open,
        initialModulesState = ModuleExpandedState.EXPANDED,
        drawerModules = {
            listOf(
                ShortcutsModule(),
                DemoActionsModule(),
                BuildModule(),
                DeviceModule(),
            )
        }
    ) { drawerState -> AppContent(drawerState) }
}

@Composable
private fun AppContent(drawerState: DrawerState) {
    Scaffold(
        topBar = {
            val title = stringResource(id = R.string.app_name)
            TopAppBar(
                elevation = 0.dp,
                title = { Text(text = title) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            drawerButton(drawerState)
        }

    }
}

@Composable
private fun drawerButton(drawerState: DrawerState) {
    Button(
        onClick = {
            if (drawerState.isOpen) {
                drawerState.close()
            } else if (drawerState.isClosed) {
                drawerState.open()
            }
        }) {
        if (drawerState.isOpen) {
            Text(text = "Close DRAWER")
        } else {
            Text(text = "Open DRAWER")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ComposeDrawerTheme {
        HomeScreen()
    }
}