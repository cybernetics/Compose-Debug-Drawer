package com.alorma.drawer_base

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.ui.test.ComposeTestRule
import androidx.ui.test.onRoot
import androidx.ui.test.printToLog

@Composable
fun TestTheme(content: @Composable() () -> Unit) {
    MaterialTheme(
        content = content
    )
}

fun ComposeTestRule.printLog(tag: String = "ComposeDrawer") {
    onRoot(useUnmergedTree = true).printToLog(tag = tag)
}
@Composable
fun createDebugDrawer(
    modules: List<DebugModule>
) {
    DrawerContent(
        drawerModules = { modules }
    )
}