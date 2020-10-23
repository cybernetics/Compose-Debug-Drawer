package com.alorma.composedrawer

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.alorma.composedrawer.modules.DemoActionsModule
import com.alorma.composedrawer.ui.ComposeDrawerTheme
import com.alorma.drawer_base.DebugDrawerLayout
import com.alorma.drawer_modules.BuildModule
import com.alorma.drawer_modules.DeviceModule

@Composable
fun HomeScreen() {
    DebugDrawerLayout(
        isDebug = { BuildConfig.DEBUG },
        initialDrawerState = DrawerValue.Open,
        drawerModules = {
            listOf(
                DemoActionsModule(),
                BuildModule(),
                DeviceModule(),
            )
        }
    ) { drawerState ->
        Scaffold(
            topBar = {
                val title = stringResource(id = R.string.app_name)
                TopAppBar(
                    elevation = 0.dp,
                    title = { Text(text = title) }
                )
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.Center,
            ) {
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