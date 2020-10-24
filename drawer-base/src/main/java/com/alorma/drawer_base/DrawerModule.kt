package com.alorma.drawer_base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp

@Composable
fun DrawerModule(
    module: DebugModule,
    modifier: Modifier? = null,
    initialModulesState: ModuleExpandedState = ModuleExpandedState.EXPANDED
) {
    val expandedState: MutableState<ModuleExpandedState> = remember {
        mutableStateOf(initialModulesState)
    }

    val semanticsModifier = Modifier.semantics {
        testTag = "Module ${module.tag}"
    }
    val moduleModifier = modifier ?: Modifier

    Column(
        modifier = semanticsModifier + Modifier.fillMaxWidth() + moduleModifier,
    ) {
        DrawerModuleHeader(
            module = module,
        ) {
            expandedState.value = !expandedState.value
        }

        val contentSemanticsModifier = Modifier.semantics {
            testTag = "Module content ${module.tag}"
        }
        if (expandedState.value == ModuleExpandedState.EXPANDED) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp) + contentSemanticsModifier,
            ) {
                module.build()
            }
        }
    }
}

enum class ModuleExpandedState {
    EXPANDED,
    COLLAPSED;

    operator fun not() = when (this) {
        EXPANDED -> COLLAPSED
        COLLAPSED -> EXPANDED
    }
}