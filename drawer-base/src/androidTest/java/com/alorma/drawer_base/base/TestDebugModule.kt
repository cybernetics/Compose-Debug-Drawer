package com.alorma.drawer_base

import androidx.compose.foundation.Text
import androidx.compose.runtime.Composable
import java.util.*

class TestDebugModule(
    override val title: String,
    override val tag: String = "Tag: ${title.toLowerCase(Locale.getDefault())}",
) : DebugModule {
    override val icon: IconType = IconType.Vector(drawableRes = R.drawable.ic_baseline_adb_24)

    @Composable
    override fun build() {
        Text(text = "Test Module $title")
    }
}