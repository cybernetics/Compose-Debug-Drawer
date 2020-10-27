package com.alorma.drawer_modules.base

import androidx.compose.runtime.Composable
import com.alorma.drawer_base.IconType
import com.alorma.drawer_modules.ActionsModule
import com.alorma.drawer_modules.DebugDrawerAction
import com.alorma.drawer_modules.R
import java.util.*

@Composable
fun TestActionModule(action: @Composable () -> List<DebugDrawerAction>) = ActionsModule(
    icon = IconType.Vector(drawableRes = R.drawable.ic_baseline_adb),
    title = "Action test",
    actions = { action() }
)