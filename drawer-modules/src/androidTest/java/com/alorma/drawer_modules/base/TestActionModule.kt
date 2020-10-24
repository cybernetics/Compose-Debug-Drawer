package com.alorma.drawer_modules.base

import androidx.compose.runtime.Composable
import com.alorma.drawer_base.IconType
import com.alorma.drawer_modules.ActionsModule
import com.alorma.drawer_modules.DebugDrawerAction
import com.alorma.drawer_modules.R
import java.util.*

@Composable
fun TestActionModule(action: @Composable () -> DebugDrawerAction) = ActionsModule(
    icon = IconType.Vector(drawableRes = R.drawable.ic_baseline_adb_24),
    title = "Action test",
    actions = { listOf(action()) }
)