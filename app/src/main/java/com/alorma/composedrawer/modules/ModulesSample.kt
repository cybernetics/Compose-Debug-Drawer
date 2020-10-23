package com.alorma.composedrawer.modules

import android.content.Context
import android.widget.Toast
import com.alorma.composedrawer.R
import com.alorma.drawer_base.IconType
import com.alorma.drawer_modules.ActionsModule
import com.alorma.drawer_modules.ButtonAction
import com.alorma.drawer_modules.SwitchAction

class DemoActionsModule(
    private val context: Context
) : ActionsModule(
    icon = IconType.Vector(R.drawable.ic_settings),
    title = "Actions",
    actions = listOf(
        ButtonAction("Button 1") {
            Toast.makeText(context, "Click Button 1", Toast.LENGTH_SHORT).show()
        },
        ButtonAction("Button 2") {
            Toast.makeText(context, "Click Button 2", Toast.LENGTH_SHORT).show()
        },
        SwitchAction("Switch 1", true) { checked ->
            Toast.makeText(context, "Switch 1 change $checked", Toast.LENGTH_SHORT).show()
        },
        SwitchAction("Switch 2", false) { checked ->
            Toast.makeText(context, "Switch 2 change $checked", Toast.LENGTH_SHORT).show()
        },
    )
)