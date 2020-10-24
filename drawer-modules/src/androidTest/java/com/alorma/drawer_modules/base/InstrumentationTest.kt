package com.alorma.drawer_modules.base

import androidx.ui.test.createComposeRule
import org.junit.Rule

abstract class InstrumentationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

}