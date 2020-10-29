package com.alorma.composedrawer

import androidx.compose.foundation.Text
import androidx.compose.material.DrawerState
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alorma.composedrawer.base.DebugDrawerScreen

@Composable
fun DetailScreen(host: NavHostController, detailId: String?) {
    DebugDrawerScreen(host) { drawerState -> AppContent(drawerState, detailId) }
}

@Composable
private fun AppContent(drawerState: DrawerState, detailId: String?) {
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                title = { Text(text = "Detail $detailId") }
            )
        }
    ) {

    }
}