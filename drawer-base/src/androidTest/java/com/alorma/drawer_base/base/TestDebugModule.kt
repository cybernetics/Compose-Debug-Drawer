package com.alorma.drawer_base.base

import androidx.compose.foundation.Text
import androidx.compose.runtime.Composable
import com.alorma.drawer_base.DebugModule
import com.alorma.drawer_base.IconType
import com.alorma.drawer_base.R
import java.util.*

class TestDebugModule(
    override val title: String,
    override val tag: String = "Tag: ${title.toLowerCase(Locale.getDefault())}",
) : DebugModule {
    override val icon: IconType = IconType.Vector(drawableRes = R.drawable.ic_baseline_adb)

    @Composable
    override fun build() {
        Text(text = "Test Module $title")
    }
}