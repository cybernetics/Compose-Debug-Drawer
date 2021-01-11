package com.alorma.developer_shortcuts

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.ContextAmbient
import com.alorma.drawer_base.DebugModule
import com.alorma.drawer_base.IconType
import com.alorma.drawer_modules.ActionsModule
import com.alorma.drawer_modules.ButtonAction
import com.alorma.drawer_modules.SwitchAction
import com.alorma.drawer_modules.TextAction
import com.chuckerteam.chucker.api.Chucker
import leakcanary.AppWatcher
import leakcanary.LeakCanary

@Composable
fun ShortcutsModule(): DebugModule {
    val context = AmbientContext.current
    val actions = listOfNotNull(
        ButtonAction(text = "Showkase", onClick = {

        }).takeIf {
            classExists("com.airbnb.android.showkase.ui.ShowkaseBrowserActivity")
        },
        ButtonAction(text = "Network logs", onClick = {
            context.startActivity(Chucker.getLaunchIntent(context))
        }).takeIf {
            classExists("com.chuckerteam.chucker.api.Chucker")
        },
        ButtonAction(text = "Notification channels", onClick = {
            val intent = Intent(
                Settings.ACTION_APP_NOTIFICATION_SETTINGS
            ).putExtra(
                Settings.EXTRA_APP_PACKAGE, context.packageName
            )
            context.startActivity(intent)
        }).takeIf { Build.VERSION.SDK_INT >= Build.VERSION_CODES.O },
        TextAction(text = "Leak Canary").takeIf {
            classExists("leakcanary.LeakCanary")
        },
        SwitchAction(text = "Enable", isChecked = false, onChange = { enable ->
            AppWatcher.config = AppWatcher.config.copy(enabled = enable)
            LeakCanary.config = LeakCanary.config.copy(dumpHeap = enable)
            LeakCanary.showLeakDisplayActivityLauncherIcon(enable)
        }).takeIf {
            classExists("leakcanary.LeakCanary")
        },
        ButtonAction(text = "Reports", onClick = {
            context.startActivity(LeakCanary.newLeakDisplayActivityIntent())
        }).takeIf {
            classExists("leakcanary.LeakCanary")
        },
        ButtonAction(text = "Dump", onClick = {
            LeakCanary.dumpHeap()
        }).takeIf {
            classExists("leakcanary.LeakCanary")
        },
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
