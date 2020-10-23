package com.alorma.drawer_base


import androidx.compose.animation.asDisposableClock
import androidx.compose.animation.core.AnimationClockObservable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.Saver
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.gesture.tapGestureFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AnimationClockAmbient
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.platform.LayoutDirectionAmbient
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/**
 * Navigation drawers provide access to destinations in your app.
 *
 * Modal navigation drawers block interaction with the rest of an app’s content with a scrim.
 * They are elevated above most of the app’s UI and don’t affect the screen’s layout grid.
 *
 * See [BottomDrawerLayout] for a layout that introduces a bottom drawer, suitable when
 * using bottom navigation.
 *
 * @sample androidx.compose.material.samples.ModalDrawerSample
 *
 * @param drawerContent composable that represents content inside the drawer
 * @param modifier optional modifier for the drawer
 * @param drawerState state of the drawer
 * @param gesturesEnabled whether or not drawer can be interacted by gestures
 * @param drawerShape shape of the drawer sheet
 * @param drawerElevation drawer sheet elevation. This controls the size of the shadow below the
 * drawer sheet
 * @param bodyContent content of the rest of the UI
 *
 * @throws IllegalStateException when parent has [Float.POSITIVE_INFINITY] width
 */
@Composable
@OptIn(ExperimentalMaterialApi::class)
fun DebugDrawerLayout(
    isDebug: () -> Boolean = { false },
    drawerColors: Colors = drawerColorsPalette,
    drawerModules: @Composable () -> List<DebugModule> = { emptyList() },
    initialDrawerState: DrawerValue = DrawerValue.Closed,
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
            val maxValue = 0f

            val anchors = mapOf(minValue to DrawerValue.Closed, maxValue to DrawerValue.Open)
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
                    fraction = { calculateFraction(minValue, maxValue, drawerState.offset.value) },
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
                        elevation = androidx.compose.material.DrawerConstants.DefaultElevation
                    ) {
                        DrawerContent(drawerModules)
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerContent(
    drawerModules: @Composable () -> List<DebugModule> = { emptyList() },
) {
    val items = drawerModules()
    LazyColumnForIndexed(
        modifier = Modifier.fillMaxSize(),
        items = items
    ) { index, module ->
        DrawerModule(module)
        if (index < items.size + 1) {
            Spacer(modifier = Modifier.preferredHeight(8.dp))
        }
    }
}

@Composable
fun DrawerModule(module: DebugModule) {
    Column {
        Surface(
            modifier = Modifier.fillMaxWidth().height(48.dp),
            color = DrawerColors.current.onSurface.compositeOverSurface(alpha = 0.12f),
            contentColor = DrawerColors.current.secondary,
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DrawerHeaderIcon(module.icon, 32.dp)
                Spacer(modifier = Modifier.preferredWidth(8.dp))
                Text(
                    modifier = Modifier,
                    text = module.title,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = DrawerColors.current.onSurface.compositeOverSurface(alpha = 0.08f)
                )
                .padding(8.dp),
        ) {
            module.build()
        }
    }
}

@Composable
private fun DrawerHeaderIcon(icon: IconType, size: Dp) {
    val modifier = Modifier.preferredSize(size = size)
    when (icon) {
        is IconType.Vector -> Icon(
            modifier = modifier,
            asset = vectorResource(
                id = icon.drawableRes
            ),
        )
        is IconType.Image -> Icon(
            modifier = modifier,
            asset = imageResource(
                id = icon.drawableRes
            ),
        )
    }
}

/**
 * Object to hold default values for [DebugDrawerLayout] and [BottomDrawerLayout]
 */
object DrawerConstants {

    /**
     * Default Elevation for drawer sheet as specified in material specs
     */
    val DefaultElevation = 16.dp

    @Composable
    val defaultScrimColor: Color
        get() = MaterialTheme.colors.onSurface.copy(alpha = ScrimDefaultOpacity)

    /**
     * Default alpha for scrim color
     */
    const val ScrimDefaultOpacity = 0.32f
}

private fun calculateFraction(a: Float, b: Float, pos: Float) =
    ((pos - a) / (b - a)).coerceIn(0f, 1f)

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

private const val DrawerStiffness = 1000f

private val AnimationSpec = SpringSpec<Float>(stiffness = DrawerStiffness)