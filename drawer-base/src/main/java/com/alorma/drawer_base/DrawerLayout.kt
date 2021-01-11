package com.alorma.drawer_base

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.gesture.tapGestureFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.WithConstraints
import androidx.compose.ui.platform.AmbientDensity
import androidx.compose.ui.platform.AmbientLayoutDirection
import androidx.compose.ui.platform.LayoutDirectionAmbient
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun DebugDrawerLayout(
    modifier: Modifier = Modifier,
    isDebug: () -> Boolean = { false },
    drawerColors: Colors = drawerColorsPalette,
    drawerModules: @Composable () -> List<DebugModule> = { emptyList() },
    initialDrawerState: DrawerValue = DrawerValue.Closed,
    initialModulesState: ModuleExpandedState = ModuleExpandedState.EXPANDED,
    bodyContent: @Composable (DrawerState) -> Unit
) {

    val drawerState = rememberDrawerState(initialValue = initialDrawerState)

    if (!isDebug()) {
        bodyContent(drawerState)
    }

    Providers(
        DrawerColors provides drawerColors
    ) {
        WithConstraints(Modifier.fillMaxSize()) {
            if (!constraints.hasBoundedWidth) {
                throw IllegalStateException("Drawer shouldn't have infinite width")
            }

            val minValue = constraints.maxWidth.toFloat()

            val anchors = mapOf(minValue to DrawerValue.Closed, 0f to DrawerValue.Open)
            val isRtl = AmbientLayoutDirection.current == LayoutDirection.Rtl

            Box(
                Modifier.swipeable(
                    state = drawerState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Horizontal,
                    enabled = true,
                    reverseDirection = isRtl,
                    velocityThreshold = DrawerVelocityThreshold,
                    resistance = null
                )
            ) {
                Box {
                    bodyContent(drawerState)
                }
                Scrim(
                    open = drawerState.isOpen,
                    onClose = { drawerState.close() },
                    fraction = { calculateFraction(minValue, drawerState.offset.value) },
                    color = DrawerDefaults.scrimColor
                )
                Box(
                    modifier = with(AmbientDensity.current) {
                        Modifier
                            .width(constraints.maxWidth.toDp())
                            .height(constraints.maxHeight.toDp())
                    }
                        .offset(x = drawerState.offset.value.dp)
                        .padding(start = VerticalDrawerPadding)
                ) {
                    Surface(
                        shape = MaterialTheme.shapes.large,
                        color = DrawerColors.current.background,
                        contentColor = DrawerColors.current.onSurface,
                        elevation = DrawerDefaults.Elevation
                    ) {
                        DrawerContent(
                            modifier = modifier,
                            drawerModules = drawerModules,
                            initialModulesState = initialModulesState
                        )
                    }
                }
            }
        }
    }
}

private fun calculateFraction(a: Float, pos: Float) =
    ((pos - a) / a).coerceIn(0f, 1f)

@Composable
private fun Scrim(
    open: Boolean,
    onClose: () -> Unit,
    fraction: () -> Float,
    color: Color
) {
    val dismissDrawer = if (open) {
        Modifier.tapGestureFilter { onClose() }
    } else {
        Modifier
    }

    Canvas(
        Modifier
            .fillMaxSize()
            .then(dismissDrawer)
    ) {
        drawRect(color, alpha = fraction())
    }
}

private val VerticalDrawerPadding = 56.dp
private val DrawerVelocityThreshold = 400.dp