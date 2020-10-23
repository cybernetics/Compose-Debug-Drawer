package com.alorma.drawer_modules

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
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

}