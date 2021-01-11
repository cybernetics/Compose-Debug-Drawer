package com.alorma.drawer_modules

import android.os.Build
import android.util.DisplayMetrics
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.ContextAmbient
import androidx.ui.tooling.preview.Preview
import com.alorma.drawer_base.DebugModule
import com.alorma.drawer_base.IconType

@Composable
fun DeviceModule(): DebugModule {

    fun getDensityString(displayMetrics: DisplayMetrics): String {
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

    val context = AmbientContext.current

    val displayMetrics = context.resources.displayMetrics
    val densityBucket = getDensityString(displayMetrics)

    val deviceMake = "Make" to Build.MANUFACTURER
    val deviceModel = "Device" to Build.MODEL
    val deviceResolution =
        "Resolution" to "${displayMetrics.heightPixels}x${displayMetrics.widthPixels}"
    val deviceDensity = "Density" to "${displayMetrics.densityDpi}dpi ($densityBucket)"
    val deviceRelease = "Release" to Build.VERSION.RELEASE
    val deviceApi = "API" to Build.VERSION.SDK_INT.toString()

    val icon = IconType.Vector(R.drawable.ic_compose_drawer_device)
    val title = "Device information"

    val items = listOf(
        deviceMake,
        deviceModel,
        deviceResolution,
        deviceDensity,
        deviceRelease,
        deviceApi
    )

    return InfoModule(icon = icon, title = title, items = items)
}

@Preview(showBackground = true)
@Composable
fun DeviceModulePreview() {
    DeviceModule().build()
}