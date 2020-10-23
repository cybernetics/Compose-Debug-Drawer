package com.alorma.composedrawer

import androidx.compose.foundation.Text
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.alorma.composedrawer.modules.Module1
import com.alorma.composedrawer.ui.ComposeDrawerTheme
import com.alorma.drawer_base.DebugDrawerLayout
import com.alorma.drawer_base.DrawerValue
import com.alorma.drawer_base.rememberDrawerState
import com.alorma.drawer_modules.DeviceModule

@Composable
fun HomeScreen() {
    DebugDrawerLayout(
        debug = { BuildConfig.DEBUG },
        drawerState = rememberDrawerState(DrawerValue.Open),
        drawerModules = {
            listOf(
                Module1,
                DeviceModule(),
            )
        }
    ) {
        Scaffold(
            topBar = {
                val title = stringResource(id = R.string.app_name)
                TopAppBar(
                    elevation = 0.dp,
                    title = { Text(text = title) }
                )
            }
        ) {

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