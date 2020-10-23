package com.alorma.drawer_modules

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

        val info = context.packageManager.getPackageInfo(context.packageName, 0)

        val infoVersion = "Version" to
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    info.longVersionCode.toString()
                } else {
                    info.versionCode.toString()
                }
        val infoName = "Name" to info.versionName
        val infoPackage = "Package" to info.packageName

        val items = listOf(
            infoVersion,
            infoName,
            infoPackage
        )

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
}