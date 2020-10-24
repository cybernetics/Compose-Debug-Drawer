package com.alorma.drawer_base

import androidx.ui.test.assertIsDisplayed
import androidx.ui.test.onNodeWithTag
import com.alorma.drawer_base.base.InstrumentationTest
import com.alorma.drawer_base.base.TestDebugModule
import com.alorma.drawer_base.base.setModuleContent
import org.junit.Test

class ModuleTagTest : InstrumentationTest() {

    @Test
    fun test_tag_module() {
        setModuleContent {
            TestDebugModule("First")
        }

        composeTestRule
            .onNodeWithTag("Module Tag: first")
            .assertIsDisplayed()
    }

    @Test
    fun test_tag_module_header() {
        setModuleContent {
            TestDebugModule("First")
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
}