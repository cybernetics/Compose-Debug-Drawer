package com.alorma.drawer_modules

import androidx.ui.test.*
import com.alorma.drawer_modules.base.InstrumentationTest
import com.alorma.drawer_modules.base.printLog
import com.alorma.drawer_modules.base.setModuleContent
import org.junit.Test

class SwitchActionTest : InstrumentationTest() {

    @Test
    fun test_switch_action() {
        var isChecked = true
        setModuleContent {
            SwitchAction(
                text = "Switch test",
                tag = "Test",
                isChecked = isChecked,
                onChange = { checked ->
                    isChecked = checked
                }
            )
        }

        composeTestRule.printLog()

        composeTestRule.onNodeWithTag("Action Test")
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(!isChecked)

        composeTestRule.onNodeWithTag("Action Test switch")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Switch test")
            .assertIsDisplayed()
    }
}