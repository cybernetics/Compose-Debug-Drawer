package com.alorma.composedrawer

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material.DrawerState
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alorma.composedrawer.base.DebugDrawerScreen

@Composable
fun DetailScreen(
    host: NavHostController,
    detailId: String?,
    tab: Int?
) {
    DebugDrawerScreen(host) { drawerState -> AppContent(drawerState, detailId, tab) }
}

@Composable
private fun AppContent(
    drawerState: DrawerState,
    detailId: String?,
    tab: Int?
) {
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                title = {
                    Column {
                        Text(text = "Detail $detailId")
                        Text(text = "Tab $tab")
                    }
                }
            )
        }
    ) {

    }
}