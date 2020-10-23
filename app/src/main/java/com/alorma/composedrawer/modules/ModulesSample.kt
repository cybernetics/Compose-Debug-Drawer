package com.alorma.composedrawer.modules

import com.alorma.composedrawer.R
import com.alorma.drawer_base.IconType
import com.alorma.drawer_modules.ActionsModule
import com.alorma.drawer_modules.DebugDrawerAction

class DemoActionsModule : ActionsModule(
    title = "Actions",
    icon = IconType.Vector(R.drawable.ic_settings),
    actions = listOf(
        DebugDrawerAction.ButtonAction("Button 1"),
        DebugDrawerAction.ButtonAction("Button 2"),
        DebugDrawerAction.ButtonAction("Button 3"),
    )
)