package com.alorma.drawer_base

import androidx.compose.runtime.Composable
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

        composeTestRule
            .onNodeWithTag("Module header Tag: first")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("Module header text Tag: first", true)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("Module header icon Tag: first", true)
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

        composeTestRule
            .onNodeWithTag("Module header Tag: first")
            .assertHasClickAction()

        composeTestRule.onNodeWithText("First")
            .assertIsDisplayed()

    }

    @Test
    fun test_module_content() {
        composeTestRule.setContent {
            TestTheme {
                createDebugDrawer(
                    listOf(
                        TestDebugModule("First")
                    )
                )
            }
        }

        composeTestRule.printLog()

        composeTestRule
            .onNodeWithTag("Module content Tag: first")
            .assertIsDisplayed()
    }

    @Test
    fun test_module_expand() {
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
            .onNodeWithTag("Module header Tag: first")
            .assertHasClickAction()
            .performClick()

        composeTestRule
            .onNodeWithTag("Module content Tag: first")
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithTag("Module header Tag: first")
            .assertHasClickAction()
            .performClick()

        composeTestRule
            .onNodeWithTag("Module content Tag: first")
            .assertExists()
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