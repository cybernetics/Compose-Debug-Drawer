package com.alorma.drawer_base

import androidx.ui.test.*
import com.alorma.drawer_base.base.InstrumentationTest
import com.alorma.drawer_base.base.TestDebugModule
import com.alorma.drawer_base.base.setModuleContent
import org.junit.Test

class ModuleBehaviourTest: InstrumentationTest() {

    @Test
    fun test_module_header() {
        setModuleContent {
            TestDebugModule("First")
        }

        composeTestRule
            .onNodeWithTag("Module header Tag: first")
            .assertHasClickAction()

        composeTestRule.onNodeWithText("First")
            .assertIsDisplayed()

    }

    @Test
    fun test_module_content() {
        setModuleContent {
            TestDebugModule("First")
        }

        composeTestRule
            .onNodeWithTag("Module content Tag: first")
            .assertIsDisplayed()
    }

    @Test
    fun test_module_expand() {
        setModuleContent {
            TestDebugModule("First")
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
}