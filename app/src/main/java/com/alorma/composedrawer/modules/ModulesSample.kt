package com.alorma.composedrawer.modules

import androidx.compose.foundation.Text
import androidx.compose.runtime.Composable
import com.alorma.composedrawer.R
import com.alorma.drawer_base.DebugModule
import com.alorma.drawer_base.IconType

object Module1 : DebugModule {
    override val icon = IconType.Vector(R.drawable.ic_account)
    override val title: String = "Account"

    @Composable
    override fun build() {
        Text(text = "Module 1 content")
    }
}

object Module2 : DebugModule {
    override val icon = IconType.Vector(R.drawable.ic_device)
    override val title: String = "Device"

    @Composable
    override fun build() {
        Text(text = "Module 2 content")
    }
}