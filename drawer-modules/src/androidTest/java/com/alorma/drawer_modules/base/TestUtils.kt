package com.alorma.drawer_modules.base

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.ui.test.ComposeTestRule
import androidx.ui.test.onRoot
import androidx.ui.test.printToLog
import com.alorma.drawer_base.DrawerContent
import com.alorma.drawer_modules.DebugDrawerAction

fun InstrumentationTest.setActionContent(content: @Composable () -> DebugDrawerAction) {
    composeTestRule.setContent {
        MaterialTheme {
            DrawerContent {
                listOf(TestActionModule(action = { listOf(content()) }))
            }
        }
    }
}

fun InstrumentationTest.setModuleContent(content: @Composable () -> List<DebugDrawerAction>) {
    composeTestRule.setContent {
        MaterialTheme {
            DrawerContent {
                listOf(TestActionModule(action = content))
            }
        }
    }
}

fun ComposeTestRule.printLog(tag: String = "ComposeDrawer") {
    onRoot(useUnmergedTree = true).printToLog(tag = tag)
}