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
fun ProfileScreen(host: NavHostController) {
    DebugDrawerScreen(host) { drawerState -> AppContent(drawerState) }
}

@Composable
private fun AppContent(drawerState: DrawerState) {
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                title = { Text(text = "Profile") }
            )
        }
    ) {

    }
}