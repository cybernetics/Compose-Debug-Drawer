package com.alorma.drawer_modules

import android.content.Context
import android.os.Build
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.AmbientEmphasisLevels
import androidx.compose.material.Divider
import androidx.compose.material.ProvideEmphasis
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.dp
import com.alorma.drawer_base.DebugModule
import com.alorma.drawer_base.DrawerColors
import com.alorma.drawer_base.IconType
import com.alorma.drawer_base.compositeOverSurface

@Composable
fun BuildModule() = object : DebugModule {
    override val icon = IconType.Vector(R.drawable.ic_compose_drawer_adb)
    override val title: String = "Build information"

    @Composable
    override fun build() {
        val context = ContextAmbient.current

        val items = try {
            obtainBuildInfo(context)
        } catch (e: NullPointerException) {
            debugBuildInfo()
        }

        Column {
            items.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier.preferredHeight(30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.high) {
                        Text(text = "${item.first}:")
                    }
                    Spacer(modifier = Modifier.preferredWidth(8.dp))
                    ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.medium) {
                        Text(text = item.second)
                    }
                }
                if (index < items.size - 1) {
                    Spacer(modifier = Modifier.preferredHeight(4.dp))
                    Divider(
                        color = DrawerColors.current.onSurface.compositeOverSurface(0.12f)
                    )
                    Spacer(modifier = Modifier.preferredHeight(4.dp))
                }
            }
        }
    }

    private fun obtainBuildInfo(context: Context): List<Pair<String, String>> {
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

    private fun debugBuildInfo(): List<Pair<String, String>> {
        val infoVersion = "Version" to "11141"
        val infoName = "Name" to "1.1.4"
        val infoPackage = "Package" to "com.alorma"

        return listOf(
            infoVersion,
            infoName,
            infoPackage
        )
    }
}