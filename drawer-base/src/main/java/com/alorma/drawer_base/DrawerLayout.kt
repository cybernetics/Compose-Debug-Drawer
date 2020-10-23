package com.alorma.drawer_base

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.gesture.tapGestureFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.platform.LayoutDirectionAmbient
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun DebugDrawerLayout(
    isDebug: () -> Boolean = { false },
    drawerColors: Colors = drawerColorsPalette,
    drawerModules: @Composable () -> List<DebugModule> = { emptyList() },
    initialDrawerState: DrawerValue = DrawerValue.Closed,
    moduleModifier: Modifier? = null,
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
            val isRtl = LayoutDirectionAmbient.current == LayoutDirection.Rtl

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
                    color = DrawerConstants.defaultScrimColor
                )
                Box(
                    modifier = with(DensityAmbient.current) {
                        Modifier
                            .width(constraints.maxWidth.toDp())
                            .height(constraints.maxHeight.toDp())
                    }.offsetPx(x = drawerState.offset)
                        .padding(start = VerticalDrawerPadding)
                ) {
                    Surface(
                        shape = MaterialTheme.shapes.large,
                        color = DrawerColors.current.background,
                        contentColor = DrawerColors.current.onSurface,
                        elevation = DrawerConstants.DefaultElevation
                    ) {
                        DrawerContent(
                            drawerModules = drawerModules,
                            moduleModifier = moduleModifier,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerContent(
    drawerModules: @Composable () -> List<DebugModule> = { emptyList() },
    moduleModifier: Modifier? = null,
) {
    val items = drawerModules()
    LazyColumnForIndexed(
        modifier = Modifier.fillMaxSize(),
        items = items
    ) { index, module ->
        DrawerModule(
            module = module,
            modifier = moduleModifier,
        )
        if (index < items.size + 1) {
            Spacer(modifier = Modifier.preferredHeight(8.dp))
        }
    }
}

@Composable
fun DrawerModule(
    module: DebugModule,
    modifier: Modifier? = null,
) {
    val semanticsModifier = Modifier.semantics {
        testTag = "Module ${module.tag}"
    }
    val moduleModifier = modifier ?: Modifier

    Column(
        modifier = semanticsModifier + Modifier.fillMaxWidth() + moduleModifier,
    ) {
        DrawerModuleHeader(
            module = module,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = DrawerColors.current.onSurface.compositeOverSurface(alpha = 0.08f)
                ).padding(8.dp),
        ) {
            module.build()
        }
    }
}

@Composable
private fun DrawerModuleHeader(
    module: DebugModule
) {
    val semanticModifier = Modifier.semantics {
        testTag = "Module header ${module.tag}"
    }
    Surface(
        modifier = semanticModifier + Modifier.fillMaxWidth().height(48.dp),
        color = DrawerColors.current.onSurface.compositeOverSurface(alpha = 0.12f),
        contentColor = DrawerColors.current.secondary,
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DrawerModuleHeaderIcon(module, 32.dp)
            Spacer(modifier = Modifier.preferredWidth(8.dp))
            DrawerModuleHeaderText(module)
        }
    }
}

@Composable
private fun DrawerModuleHeaderText(
    module: DebugModule
) {
    val semanticsModifier = Modifier.semantics {
        testTag = "Module header text ${module.tag}"
    }
    Text(
        modifier = semanticsModifier,
        text = module.title,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
private fun DrawerModuleHeaderIcon(module: DebugModule, size: Dp) {

    val semanticsModifier = Modifier.semantics {
        testTag = "Module header icon ${module.tag}"
    }
    val modifier = semanticsModifier + Modifier.preferredSize(size = size)
    when (module.icon) {
        is IconType.Vector -> Icon(
            modifier = modifier,
            asset = vectorResource(
                id = module.icon.drawableRes
            ),
        )
        is IconType.Image -> Icon(
            modifier = modifier,
            asset = imageResource(
                id = module.icon.drawableRes
            ),
        )
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