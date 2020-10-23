package com.alorma.drawer_modules

import android.os.Build
import android.util.DisplayMetrics
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
fun DeviceModule() = object : DebugModule {
    override val icon = IconType.Vector(R.drawable.ic_compose_drawer_device)
    override val title: String = "Device"

    @Composable
    override fun build() {
        val context = ContextAmbient.current

        val displayMetrics = context.resources.displayMetrics
        val densityBucket = getDensityString(displayMetrics)

        val deviceMake = "Make" to Build.MANUFACTURER
        val deviceModel = "Device" to Build.MODEL
        val deviceResolution =
            "Resolution" to "${displayMetrics.heightPixels}x${displayMetrics.widthPixels}"
        val deviceDensity = "Density" to "${displayMetrics.densityDpi}dpi ($densityBucket)"
        val deviceRelease = "Release" to Build.VERSION.RELEASE
        val deviceApi = "API" to Build.VERSION.SDK_INT.toString()

        val items = listOf(
            deviceMake,
            deviceModel,
            deviceResolution,
            deviceDensity,
            deviceRelease,
            deviceApi
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

    private fun getDensityString(displayMetrics: DisplayMetrics): String {
        return when (displayMetrics.densityDpi) {
            DisplayMetrics.DENSITY_LOW -> "ldpi"
            DisplayMetrics.DENSITY_MEDIUM -> "mdpi"
            DisplayMetrics.DENSITY_HIGH -> "hdpi"
            DisplayMetrics.DENSITY_XHIGH -> "xhdpi"
            DisplayMetrics.DENSITY_XXHIGH -> "xxhdpi"
            DisplayMetrics.DENSITY_XXXHIGH -> "xxxhdpi"
            DisplayMetrics.DENSITY_TV -> "tvdpi"
            else -> displayMetrics.densityDpi.toString()
        }
    }
}