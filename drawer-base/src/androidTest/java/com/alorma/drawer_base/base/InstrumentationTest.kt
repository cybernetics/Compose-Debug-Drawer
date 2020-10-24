package com.alorma.drawer_base

import androidx.ui.test.createComposeRule
import org.junit.Rule

interface InstrumentationTest {
    @get:Rule
    val composeTestRule
        get() = createComposeRule()
}