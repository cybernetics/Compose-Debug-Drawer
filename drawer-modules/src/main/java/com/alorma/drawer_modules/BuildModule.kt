package com.alorma.drawer_modules

import android.content.Context
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.ContextAmbient
import androidx.ui.tooling.preview.Preview
import com.alorma.drawer_base.DebugModule
import com.alorma.drawer_base.IconType

@Composable
fun BuildModule(): DebugModule {
    val context = AmbientContext.current

    fun obtainBuildInfo(context: Context): List<Pair<String, String>> {
        val info = context.packageManager.getPackageInfo(context.packageName, 0)

        val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            info.longVersionCode.toString()
        } else {
            info.versionCode.toString()
        }

        val infoVersion = "Version" to versionCode
        val infoName = "Name" to info.versionName
        val infoPackage = "Package" to info.packageName

        return listOf(
            infoVersion,
            infoName,
            infoPackage
        )
    }

    fun obtainDebugInfo(): List<Pair<String, String>> {
        val infoVersion = "Version" to "11141"
        val infoName = "Name" to "1.1.4"
        val infoPackage = "Package" to "com.alorma"

        return listOf(
            infoVersion,
            infoName,
            infoPackage
        )
    }

    val items = try {
        obtainBuildInfo(context)
    } catch (e: NullPointerException) {
        obtainDebugInfo()
    }

    val icon = IconType.Vector(R.drawable.ic_compose_drawer_adb)
    val title = "Build information"

    return InfoModule(icon = icon, title = title, items = items)
}

@Preview(showBackground = true)
@Composable
fun BuildModulePreview() {
    BuildModule().build()
}