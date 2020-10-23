package com.alorma.drawer_modules

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.AmbientEmphasisLevels
import androidx.compose.material.ProvideEmphasis
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.alorma.drawer_base.DebugModule
import com.alorma.drawer_base.DrawerDivider
import com.alorma.drawer_base.IconType

@Composable
fun InfoModule(
    icon: IconType,
    title: String,
    items: List<Pair<String, String>>
) = object : DebugModule {
    override val icon: IconType = icon
    override val title: String = title

    @Composable
    override fun build() {
        Column {
            items.forEachIndexed { index, item ->
                Column {
                    InfoContent(item)
                    if (index < items.size - 1) {
                        DrawerDivider()
                    }
                }
            }
        }

    }

    @Composable
    private fun InfoContent(
        item: Pair<String, String>
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .preferredHeight(30.dp)
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.preferredWidth(100.dp)
            ) {
                ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.high) {
                    Text(
                        text = item.first,
                        textAlign = TextAlign.Start,
                    )
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.high) {
                    Text(
                        text = item.second,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InfoModulePreview() {
    val items = listOf(
        "Value" to "A",
        "Value" to "B",
        "Value large" to "C"
    )
    InfoModule(
        icon = IconType.Vector(
            drawableRes = R.drawable.ic_compose_drawer_adb
        ),
        title = "Preview",
        items = items,
    ).build()
}