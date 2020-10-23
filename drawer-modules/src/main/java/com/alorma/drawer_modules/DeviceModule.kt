package com.alorma.drawer_modules

import androidx.compose.foundation.Text
import androidx.compose.runtime.Composable
import com.alorma.drawer_base.DebugModule
import com.alorma.drawer_base.IconType

class DeviceModule : DebugModule {
    override val icon = IconType.Vector(R.drawable.ic_compose_drawer_device)
    override val title: String = "Device"

    @Composable
    override fun build() {
        Text(text = "Device info")
    }
}