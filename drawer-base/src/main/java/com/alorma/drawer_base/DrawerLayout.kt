package com.alorma.drawer_base


import androidx.compose.animation.asDisposableClock
import androidx.compose.animation.core.AnimationClockObservable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.Saver
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.gesture.tapGestureFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.AnimationClockAmbient
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.platform.LayoutDirectionAmbient
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/**
 * Possible values of [DrawerState].
 */
enum class DrawerValue {
    /**
     * The state of the drawer when it is closed.
     */
    Closed,

    /**
     * The state of the drawer when it is open.
     */
    Open
}

/**
 * State of the [DebugDrawerLayout] composable.
 *
 * @param initialValue The initial value of the state.
 * @param clock The animation clock that will be used to drive the animations.
 * @param confirmStateChange Optional callback invoked to confirm or veto a pending state change.
 */
@Suppress("NotCloseable")
@OptIn(ExperimentalMaterialApi::class)
@Stable
class DrawerState(
    initialValue: DrawerValue,
    clock: AnimationClockObservable,
    confirmStateChange: (DrawerValue) -> Boolean = { true }
) : SwipeableState<DrawerValue>(
    initialValue = initialValue,
    clock = clock,
    animationSpec = AnimationSpec,
    confirmStateChange = confirmStateChange
) {
    /**
     * Whether the drawer is open.
     */
    val isOpen: Boolean
        get() = value == DrawerValue.Open

    /**
     * Whether the drawer is closed.
     */
    val isClosed: Boolean
        get() = value == DrawerValue.Closed

    /**
     * Open the drawer with an animation.
     *
     * @param onOpened Optional callback invoked when the drawer has finished opening.
     */
    fun open(onOpened: (() -> Unit)? = null) {
        animateTo(
            DrawerValue.Open,
            onEnd = { endReason, endValue ->
                if (endReason != AnimationEndReason.Interrupted && endValue == DrawerValue.Open) {
                    onOpened?.invoke()
                }
            }
        )
    }

    /**
     * Close the drawer with an animation.
     *
     * @param onClosed Optional callback invoked when the drawer has finished closing.
     */
    fun close(onClosed: (() -> Unit)? = null) {
        animateTo(
            DrawerValue.Closed,
            onEnd = { endReason, endValue ->
                if (endReason != AnimationEndReason.Interrupted && endValue == DrawerValue.Closed) {
                    onClosed?.invoke()
                }
            }
        )
    }

    companion object {
        /**
         * The default [Saver] implementation for [DrawerState].
         */
        fun Saver(
            clock: AnimationClockObservable,
            confirmStateChange: (DrawerValue) -> Boolean
        ) = Saver<DrawerState, DrawerValue>(
            save = { it.value },
            restore = { DrawerState(it, clock, confirmStateChange) }
        )
    }
}

/**
 * Create and [remember] a [DrawerState] with the default animation clock.
 *
 * @param initialValue The initial value of the state.
 * @param confirmStateChange Optional callback invoked to confirm or veto a pending state change.
 */
@Composable
fun rememberDrawerState(
    initialValue: DrawerValue,
    confirmStateChange: (DrawerValue) -> Boolean = { true }
): DrawerState {
    val clock = AnimationClockAmbient.current.asDisposableClock()
    return rememberSavedInstanceState(
        clock,
        saver = DrawerState.Saver(clock, confirmStateChange)
    ) {
        DrawerState(initialValue, clock, confirmStateChange)
    }
}

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
 * @param drawerBackgroundColor background color to be used for the drawer sheet
 * @param drawerContentColor color of the content to use inside the drawer sheet. Defaults to
 * either the matching `onFoo` color for [drawerBackgroundColor], or, if it is not a color from
 * the theme, this will keep the same value set above this Surface.
 * @param scrimColor color of the scrim that obscures content when the drawer is open
 * @param bodyContent content of the rest of the UI
 *
 * @throws IllegalStateException when parent has [Float.POSITIVE_INFINITY] width
 */
@Composable
@OptIn(ExperimentalMaterialApi::class)
fun DebugDrawerLayout(
    debug: () -> Boolean = { false },
    drawerContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    gesturesEnabled: Boolean = true,
    drawerShape: Shape = MaterialTheme.shapes.large,
    drawerElevation: Dp = DrawerConstants.DefaultElevation,
    drawerBackgroundColor: Color = MaterialTheme.colors.surface,
    drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
    scrimColor: Color = DrawerConstants.defaultScrimColor,
    bodyContent: @Composable () -> Unit
) {
    if (debug()) {
        DebugDrawerLayout(
            drawerContent = drawerContent,
            modifier = modifier,
            drawerState = drawerState,
            gesturesEnabled = gesturesEnabled,
            drawerShape = drawerShape,
            drawerElevation = drawerElevation,
            drawerBackgroundColor = drawerBackgroundColor,
            drawerContentColor = drawerContentColor,
            scrimColor = scrimColor,
            bodyContent = bodyContent,
        )
    } else {
        bodyContent()
    }
}

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
 * @param drawerBackgroundColor background color to be used for the drawer sheet
 * @param drawerContentColor color of the content to use inside the drawer sheet. Defaults to
 * either the matching `onFoo` color for [drawerBackgroundColor], or, if it is not a color from
 * the theme, this will keep the same value set above this Surface.
 * @param scrimColor color of the scrim that obscures content when the drawer is open
 * @param bodyContent content of the rest of the UI
 *
 * @throws IllegalStateException when parent has [Float.POSITIVE_INFINITY] width
 */
@Composable
@OptIn(ExperimentalMaterialApi::class)
fun DebugDrawerLayout(
    drawerContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    gesturesEnabled: Boolean = true,
    drawerShape: Shape = MaterialTheme.shapes.large,
    drawerElevation: Dp = DrawerConstants.DefaultElevation,
    drawerBackgroundColor: Color = MaterialTheme.colors.surface,
    drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
    scrimColor: Color = DrawerConstants.defaultScrimColor,
    bodyContent: @Composable () -> Unit
) {
    WithConstraints(modifier.fillMaxSize()) {
        if (!constraints.hasBoundedWidth) {
            throw IllegalStateException("Drawer shouldn't have infinite width")
        }

        val minValue = constraints.maxWidth.toFloat()
        val maxValue = VerticalDrawerPadding.value

        val anchors = mapOf(minValue to DrawerValue.Closed, maxValue to DrawerValue.Open)
        val isRtl = LayoutDirectionAmbient.current == LayoutDirection.Rtl
        Box(
            Modifier.swipeable(
                state = drawerState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal,
                enabled = gesturesEnabled,
                reverseDirection = isRtl,
                velocityThreshold = DrawerVelocityThreshold,
                resistance = null
            )
        ) {
            Box {
                bodyContent()
            }
            Scrim(
                open = drawerState.isOpen,
                onClose = { drawerState.close() },
                fraction = { calculateFraction(minValue, maxValue, drawerState.offset.value) },
                color = scrimColor
            )

            Surface(
                modifier = with(DensityAmbient.current) {
                    Modifier.preferredSizeIn(
                        minWidth = constraints.minWidth.toDp(),
                        minHeight = constraints.minHeight.toDp(),
                        maxWidth = constraints.maxWidth.toDp(),
                        maxHeight = constraints.maxHeight.toDp()
                    )
                }.offsetPx(x = drawerState.offset).padding(start = VerticalDrawerPadding),
                shape = drawerShape,
                color = drawerBackgroundColor,
                contentColor = drawerContentColor,
                elevation = drawerElevation
            ) {
                Column(Modifier.fillMaxSize(), children = drawerContent)
            }
        }
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