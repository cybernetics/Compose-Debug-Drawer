package com.alorma.composedrawer.modules

import android.content.Context
import android.widget.Toast
import com.alorma.composedrawer.R
import com.alorma.drawer_base.IconType
import com.alorma.drawer_modules.ActionsModule
import com.alorma.drawer_modules.DebugDrawerAction

class DemoActionsModule(
    private val context: Context
) : ActionsModule(
    icon = IconType.Vector(R.drawable.ic_settings),
    title = "Actions",
    actions = listOf(
        DebugDrawerAction.ButtonAction("Button 1") {
            Toast.makeText(context, "Click Button 1", Toast.LENGTH_SHORT).show()
        },
        DebugDrawerAction.ButtonAction("Button 2") {
            Toast.makeText(context, "Click Button 2", Toast.LENGTH_SHORT).show()
        },
        DebugDrawerAction.ButtonAction("Button 3") {
            Toast.makeText(context, "Click Button 3", Toast.LENGTH_SHORT).show()
        },
    )
)