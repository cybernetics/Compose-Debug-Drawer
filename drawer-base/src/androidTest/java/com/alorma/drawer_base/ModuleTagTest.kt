package com.alorma.drawer_base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collection.MutableVector
import androidx.ui.test.*
import org.junit.Rule
import org.junit.Test

class ModuleTagTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_tag_module() {
        composeTestRule.setContent {
            TestTheme {
                createDebugDrawer(
                    listOf(
                        TestDebugModule("First")
                    )
                )
            }
        }

        composeTestRule
            .onNodeWithTag("Module Tag: first")
            .assertIsDisplayed()
    }

    @Test
    fun test_tag_module_header() {
        composeTestRule.setContent {
            TestTheme {
                createDebugDrawer(
                    listOf(
                        TestDebugModule("First")
                    )
                )
            }
        }

        composeTestRule.onNodeWithText("First").assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("Module header Tag: first")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("Module header icon Tag: first")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("Module header text Tag: first")
            .assertIsDisplayed()
    }

    @Test
    fun test_module_header() {
        composeTestRule.setContent {
            TestTheme {
                createDebugDrawer(
                    listOf(
                        TestDebugModule("First")
                    )
                )
            }
        }

        composeTestRule.onNodeWithTag("Module header icon Tag: first")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("First")
            .assertIsDisplayed()

    }

    @Composable
    private fun createDebugDrawer(
        modules: List<DebugModule>
    ) {
        DrawerContent(
            drawerModules = { modules }
        )
    }
}