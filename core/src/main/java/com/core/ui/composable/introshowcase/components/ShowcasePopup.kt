package com.core.ui.composable.introshowcase.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.ui.composable.ActionButton
import com.core.ui.theme.TransparencyBlack40
import com.core.ui.model.ArrowShowCaseType
import com.core.ui.model.IntroShowCaseModel
import com.test.core.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

@Composable
fun ShowcasePopup(
    state: IntroShowcaseState,
    dismissOnClickOutside: Boolean,
    onShowCaseCompleted: () -> Unit,
) {
    state.currentTarget?.let {
        ShowcaseWindow {
            ShowcaseContent(
                target = it,
                dismissOnClickOutside = dismissOnClickOutside,
            ) {
                state.currentTargetIndex++
                if (state.currentTarget == null) {
                    onShowCaseCompleted()
                }
            }
        }
    }
}

@Composable
internal fun ShowcaseContent(
    target: IntroShowcaseTargets,
    dismissOnClickOutside: Boolean,
    onShowcaseCompleted: () -> Unit
) {

    val targetCords = target.coordinates
    val targetRect = targetCords.boundsInWindow()

    var dismissShowcaseRequest by remember(target) { mutableStateOf(false) }

    val maxDimension =
        max(targetCords.size.width.absoluteValue, targetCords.size.height.absoluteValue)
    val targetRadius = maxDimension / 2f + 40f

    val animationSpec = infiniteRepeatable<Float>(
        animation = tween(2000, easing = FastOutLinearInEasing),
        repeatMode = RepeatMode.Restart,
    )

    var outerOffset by remember(target) {
        mutableStateOf(Offset(0f, 0f))
    }

    var outerRadius by remember(target) {
        mutableFloatStateOf(0f)
    }

    val outerAnimatable = remember { Animatable(0.6f) }
    val outerAlphaAnimatable = remember(target) { Animatable(0f) }

    val animatables = remember(target) {
        listOf(
            Animatable(0f),
            Animatable(0f)
        )
    }

    LaunchedEffect(target) {
        outerAnimatable.snapTo(0.6f)

        outerAnimatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing,
            ),
        )
    }

    LaunchedEffect(target) {
        outerAlphaAnimatable.animateTo(
            targetValue = target.style.backgroundAlpha,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing,
            ),
        )
    }

    LaunchedEffect(dismissShowcaseRequest) {
        if (dismissShowcaseRequest) {
            launch {
                outerAlphaAnimatable.animateTo(
                    0f,
                    animationSpec = tween(
                        durationMillis = 400
                    )
                )
            }
            launch {
                outerAnimatable.animateTo(
                    targetValue = 0.6f,
                    animationSpec = tween(
                        durationMillis = 700,
                        easing = FastOutSlowInEasing,
                    )
                )
            }
            delay(350)
            onShowcaseCompleted()
        }
    }

    animatables.forEachIndexed { index, animatable ->
        LaunchedEffect(animatable) {
            delay(index * 1000L)
            animatable.animateTo(
                targetValue = 1f, animationSpec = animationSpec
            )
        }
    }

    Box(
        modifier = Modifier
            .alpha(outerAlphaAnimatable.value)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(target) {
                    detectTapGestures { tapOffeset ->
                        if (targetRect.contains(tapOffeset)) {
                            dismissShowcaseRequest = true
                        }
                    }
                }
                .let {
                    if (dismissOnClickOutside) {
                        it.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { dismissShowcaseRequest = true }
                    } else it
                }
                .graphicsLayer(alpha = 0.99f)
        ) {

            drawRect(
                color = target.style.backgroundColor,
                size = Size(size.width, size.height)
            )

            drawRoundRect(
                color = Color.Black,
                size = Size(targetRect.width, targetRect.height),
                cornerRadius = CornerRadius(70f),
                topLeft = Offset(targetRect.left, targetRect.top)
            )

            drawRoundRect(
                color = Color.Black,
                size = Size(targetRect.width, targetRect.height),
                cornerRadius = CornerRadius(70f),
                blendMode = BlendMode.Xor,
                topLeft = Offset(targetRect.left, targetRect.top)
            )

            val stroke = Stroke(
                width = 4f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
            )

            val left = targetRect.left
            val top = targetRect.top
            val right = targetRect.right
            val bottom = targetRect.bottom

            drawRoundRect(
                color = Color.White,
                topLeft = Offset(left, top),
                size = Size(right - left, bottom - top),
                cornerRadius = CornerRadius(70f, 70f),
                style = stroke
            )
        }

        ShowCaseText(
            target,
            targetRect,
            targetRadius,
            target.introShowCaseModel,
            updateContentCoordinates = { textCoords ->
                val contentRect = textCoords.boundsInWindow()
                val outerRect = getOuterRect(contentRect, targetRect)
                outerOffset = outerRect.center
                outerRadius = getOuterRadius(outerRect) + targetRadius
            },
            onClick = {
                dismissShowcaseRequest = true
            }
        )
    }
}


@Composable
private fun ShowCaseText(
    currentTarget: IntroShowcaseTargets,
    boundsInParent: Rect,
    targetRadius: Float,
    introShowCaseModel: IntroShowCaseModel,
    updateContentCoordinates: (LayoutCoordinates) -> Unit,
    onClick: () -> Unit
) {

    var contentOffsetY by remember(currentTarget) { mutableFloatStateOf(0f) }
    val density = LocalDensity.current
    val screenHeight = with(density) { LocalConfiguration.current.screenHeightDp.dp.toPx() }

    Box(
        content = {
            IntroScreen(introShowCaseModel, onClick)
        },
        modifier = Modifier
            .offset { IntOffset(0, contentOffsetY.roundToInt()) }
            .onGloballyPositioned { layoutCoordinates ->
                updateContentCoordinates(layoutCoordinates)
                val contentHeight = layoutCoordinates.size.height.toFloat()

                val possibleTop = boundsInParent.top - contentHeight
                val possibleBottom = boundsInParent.bottom

                contentOffsetY = if (possibleBottom + contentHeight <= screenHeight) {
                    possibleBottom - currentTarget.style.paddingTop + currentTarget.style.paddingBottom
                } else {
                    possibleTop + currentTarget.style.paddingTop - currentTarget.style.paddingBottom
                }
            }
    )

}

@Composable
fun IntroScreen(
    introShowCaseModel: IntroShowCaseModel,
    onClick: () -> Unit
) {
    val modifier =
        if (introShowCaseModel.arrowShowCaseType != ArrowShowCaseType.LEFT_TOP) Modifier.padding(top = 22.dp)
        else Modifier

    val horizontalAlignment = when (introShowCaseModel.arrowShowCaseType) {
        ArrowShowCaseType.LEFT_TOP -> Alignment.Start
        ArrowShowCaseType.RIGHT_BOTTOM -> Alignment.End
        else -> Alignment.CenterHorizontally
    }
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        when (introShowCaseModel.arrowShowCaseType) {
            ArrowShowCaseType.TOP -> {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_top),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.padding(bottom = 22.dp)
                )
            }

            ArrowShowCaseType.LEFT_TOP -> {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_top_left),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.padding(start = 64.dp, bottom = 9.dp)
                )
            }

            ArrowShowCaseType.RIGHT_BOTTOM -> {
            }
        }

        Card(
            shape = RoundedCornerShape(14),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 41.dp, end = 41.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Text(
                    text = stringResource(id = introShowCaseModel.title),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, top = 24.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                ActionButton(
                    text = stringResource(id = introShowCaseModel.titleAction),
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 1.dp, minWidth = 0.dp)
                        .padding(start = 68.dp, end = 68.dp, top = 24.dp, bottom = 24.dp),
                    contentPadding = PaddingValues(vertical = 0.dp, horizontal = 0.dp),
                    height = 40.dp,
                    onClick = {
                        onClick()
                    }
                )
            }
        }

        if (introShowCaseModel.arrowShowCaseType == ArrowShowCaseType.RIGHT_BOTTOM) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right_bottom),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.padding(top = 16.dp, end = 60.dp)
            )
        }
    }
}

private fun getOuterRect(contentRect: Rect, targetRect: Rect): Rect {

    val topLeftX = min(contentRect.topLeft.x, targetRect.topLeft.x)
    val topLeftY = min(contentRect.topLeft.y, targetRect.topLeft.y)
    val bottomRightX = max(contentRect.bottomRight.x, targetRect.bottomRight.x)
    val bottomRightY = max(contentRect.bottomRight.y, targetRect.bottomRight.y)

    return Rect(topLeftX, topLeftY, bottomRightX, bottomRightY)
}

private fun getOuterRadius(outerRect: Rect): Float {
    val d = sqrt(
        outerRect.height.toDouble().pow(2.0)
                + outerRect.width.toDouble().pow(2.0)
    ).toFloat()

    return (d / 2f)
}

data class IntroShowcaseTargets(
    val index: Int,
    val coordinates: LayoutCoordinates,
    val style: ShowcaseStyle = ShowcaseStyle.Default,
    val introShowCaseModel: IntroShowCaseModel
)

class ShowcaseStyle(
    val backgroundColor: Color = TransparencyBlack40,
    val backgroundAlpha: Float = DEFAULT_BACKGROUND_RADIUS,
    val paddingTop: Float = 0f,
    val paddingBottom: Float = 0f,
) {

    fun copy(
        backgroundColor: Color = this.backgroundColor,
        backgroundAlpha: Float = this.backgroundAlpha,
        paddingTop: Float = this.paddingTop,
        paddingBottom: Float = this.paddingBottom,
    ): ShowcaseStyle {

        return ShowcaseStyle(
            backgroundColor = backgroundColor,
            backgroundAlpha = backgroundAlpha,
            paddingTop,
            paddingBottom
        )
    }

    companion object {
        private const val DEFAULT_BACKGROUND_RADIUS = 0.98f

        /**
         * Constant for default text style.
         */
        @Stable
        val Default = ShowcaseStyle()
    }
}