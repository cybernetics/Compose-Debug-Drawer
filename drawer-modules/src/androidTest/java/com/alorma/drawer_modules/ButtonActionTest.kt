package com.alorma.drawer_modules

import androidx.ui.test.*
import com.alorma.drawer_modules.base.InstrumentationTest
import com.alorma.drawer_modules.base.printLog
import com.alorma.drawer_modules.base.setModuleContent
import org.junit.Test

class ButtonActionTest : InstrumentationTest() {

    @Test
    fun test_switch_action() {
        var clickedTimes = 0
        setModuleContent {
            ButtonAction(
                text = "Button test",
                tag = "Test",
                onClick = {
                    clickedTimes += 1
                }
            )
        }

        composeTestRule.onNodeWithTag("Action Test")
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()
            .performClick()
            .performClick()

        assert(clickedTimes == 3)

        composeTestRule.onNodeWithText("Button test")
            .assertIsDisplayed()
    }
}