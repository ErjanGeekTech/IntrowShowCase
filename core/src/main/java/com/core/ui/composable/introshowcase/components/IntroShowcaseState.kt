package com.core.ui.composable.introshowcase.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import com.core.ui.model.IntroShowCaseModel

@Composable
fun rememberIntroShowcaseState(
    initialIndex: Int = 0,
): IntroShowcaseState {
    return remember {
        IntroShowcaseState(
            initialIndex = initialIndex,
        )
    }
}

/**
 * Modifier that marks Compose UI element as a target for [IntroShowCaseTarget]
 */
internal fun Modifier.introShowcaseTarget(
    state: IntroShowcaseState,
    index: Int,
    style: ShowcaseStyle = ShowcaseStyle.Default,
    introShowCaseModel: IntroShowCaseModel
): Modifier = onGloballyPositioned { coordinates ->
    state.targets[index] = IntroShowcaseTargets(
        index = index,
        coordinates = coordinates,
        style = style,
        introShowCaseModel = introShowCaseModel
    )
}

/**
 * State class for managing the state of the IntroShowcase. Tracks the current target index and
 * associated targets.
 */
class IntroShowcaseState internal constructor(
    initialIndex: Int,
) {

    internal var targets = mutableStateMapOf<Int, IntroShowcaseTargets>()

    var currentTargetIndex by mutableIntStateOf(initialIndex)
        internal set

    val currentTarget: IntroShowcaseTargets?
        get() = targets[currentTargetIndex]

    /**
     * Resets the state to its initial values, effectively restarting the showcase.
     */
    fun reset() {
        currentTargetIndex = 0
    }
}