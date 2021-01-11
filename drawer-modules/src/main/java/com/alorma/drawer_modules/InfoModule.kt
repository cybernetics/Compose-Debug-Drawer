package com.alorma.drawer_modules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
                    DebugModuleInfoContent(item.first, item.second)
                    if (index < items.size - 1) {
                        DrawerDivider()
                    }
                }
            }
        }

    }
}

@Composable
fun DebugModuleInfoContent(
    key: String,
    value: String,
    onClick: ((String, String) -> Unit)? = null
) {

    val clickModifier = if (onClick == null) {
        Modifier
    } else {
        Modifier.clickable(onClick = { onClick(key, value) })
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.preferredWidth(80.dp)
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.body2) {
                Providers(AmbientContentAlpha provides ContentAlpha.high) {
                    Text(
                        text = key,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = MaterialTheme.shapes.medium)
                .then(clickModifier)
                .then(Modifier.padding(8.dp))
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.body2) {
                ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.medium) {
                    Text(
                        text = value,
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