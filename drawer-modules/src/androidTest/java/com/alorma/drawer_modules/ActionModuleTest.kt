package com.alorma.drawer_modules

import androidx.ui.test.*
import com.alorma.drawer_modules.base.InstrumentationTest
import com.alorma.drawer_modules.base.setModuleContent
import org.junit.Test

class ActionModuleTest : InstrumentationTest() {

    @Test
    fun test_action_module() {
        var clickedTimes = 0
        var isChecked = true
        setModuleContent {
            listOf(
                ButtonAction(
                    text = "Button test",
                    tag = "Button",
                    onClick = {
                        clickedTimes += 1
                    }
                ),
                SwitchAction(
                    text = "Switch test",
                    tag = "Switch",
                    isChecked = isChecked,
                    onChange = { checked ->
                        isChecked = checked
                    }
                )
            )
        }

        composeTestRule.onNodeWithTag("Action Button")
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()
            .performClick()
            .performClick()

        assert(clickedTimes == 3)

        composeTestRule.onNodeWithTag("Action Switch")
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(!isChecked)
    }
}