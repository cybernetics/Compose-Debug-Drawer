package com.alorma.drawer_base.base

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.ui.test.ComposeTestRule
import androidx.ui.test.onRoot
import androidx.ui.test.printToLog
import com.alorma.drawer_base.DebugModule
import com.alorma.drawer_base.DrawerContent

fun InstrumentationTest.setModuleContent(content: @Composable () -> DebugModule) {
    composeTestRule.setContent {
        MaterialTheme {
            DrawerContent(
                drawerModules = { listOf(content()) }
            )
        }
    }
}

fun ComposeTestRule.printLog(tag: String = "ComposeDrawer") {
    onRoot(useUnmergedTree = true).printToLog(tag = tag)
}