package com.alorma.drawer_modules

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Text
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alorma.drawer_base.DebugModule
import com.alorma.drawer_base.DrawerColors
import com.alorma.drawer_base.IconType

open class ActionsModule(
    override val icon: IconType,
    override val title: String,
    private val actions: List<DebugDrawerAction>
) : DebugModule {

    @Composable
    override fun build() {
        ContextAmbient
        Column {
            actions.forEachIndexed { index, action ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    action.build(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                if (index < actions.size - 1) {
                    Spacer(modifier = Modifier.preferredHeight(8.dp))
                }
            }
        }
    }
}

sealed class DebugDrawerAction {
    @Composable
    abstract fun build(modifier: Modifier)
}

data class ButtonAction(
    val text: String,
    val onClick: () -> Unit
) : DebugDrawerAction() {

    @Composable
    override fun build(modifier: Modifier) {
        Button(
            modifier = modifier,
            backgroundColor = DrawerColors.current.primary,
            contentColor = DrawerColors.current.onPrimary,
            onClick = onClick,
            content = { Text(text) }
        )
    }
}

data class SwitchAction(
    val text: String,
    val isChecked: Boolean,
    val state: MutableState<Boolean> = mutableStateOf(isChecked),
    val onChange: (checked: Boolean) -> Unit,
) : DebugDrawerAction() {

    @Composable
    override fun build(modifier: Modifier) {
        val checkedStateX: MutableState<Boolean> = remember { mutableStateOf(isChecked) }

        Row(
            modifier = modifier
                .preferredHeight(36.dp)
                .clip(shape = MaterialTheme.shapes.medium)
                .border(
                    border = BorderStroke(width = 1.dp, color = DrawerColors.current.primary),
                    shape = MaterialTheme.shapes.medium
                )
                .clickable(onClick = {
                    onSwitchChange(
                        checkedState = checkedStateX,
                        checked = !checkedStateX.value
                    )
                })
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Start,
            )
            Spacer(
                modifier = Modifier.fillMaxHeight().weight(1f)
            )
            Switch(
                color = DrawerColors.current.primary,
                checked = checkedStateX.value,
                onCheckedChange = { checked ->
                    onSwitchChange(
                        checkedState = checkedStateX,
                        checked = checked
                    )
                },
            )
        }
    }

    private fun onSwitchChange(checkedState: MutableState<Boolean>, checked: Boolean) {
        checkedState.value = checked
        onChange(checkedState.value)
    }
}
