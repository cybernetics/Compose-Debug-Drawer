package com.alorma.developer_shortcuts

import androidx.compose.runtime.Composable
import com.alorma.drawer_base.DebugModule
import com.alorma.drawer_base.IconType
import com.alorma.drawer_modules.ActionsModule
import com.alorma.drawer_modules.ButtonAction

@Composable
fun ShortcutsModule(): DebugModule {
    val actions = listOfNotNull(
        ButtonAction(text = "Showkase", onClick = {

        }).takeIf {
            classExists("com.airbnb.android.showkase.ui.ShowkaseBrowserActivity")
        }
    )

    return ActionsModule(
        icon = IconType.Vector(drawableRes = R.drawable.ic_compose_drawer_dev),
        title = "Developer Shortcuts",
        actions = { actions }
    )
}

private fun classExists(className: String): Boolean {
    return try {
        Class.forName(className)
        true
    } catch (ignored: ClassNotFoundException) {
        false
    }
}
