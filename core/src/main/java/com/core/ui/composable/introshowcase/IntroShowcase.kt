package com.core.ui.composable.introshowcase

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.core.ui.model.IntroShowCaseModel
import com.core.ui.composable.introshowcase.components.IntroShowcaseState
import com.core.ui.composable.introshowcase.components.ShowcasePopup
import com.core.ui.composable.introshowcase.components.ShowcaseStyle
import com.core.ui.composable.introshowcase.components.introShowcaseTarget
import com.core.ui.composable.introshowcase.components.rememberIntroShowcaseState

@Composable
fun IntroShowcase(
    showIntroShowCase: Boolean,
    onShowCaseCompleted: () -> Unit,
    state: IntroShowcaseState = rememberIntroShowcaseState(),
    dismissOnClickOutside: Boolean = false,
    content: @Composable IntroShowcaseScope.() -> Unit,
) {
    val scope = remember(state) {
        IntroShowcaseScope(state)
    }

    scope.content()

    if (showIntroShowCase) {
        ShowcasePopup(
            state = state,
            dismissOnClickOutside = dismissOnClickOutside,
            onShowCaseCompleted = onShowCaseCompleted,
        )
    }
}


class IntroShowcaseScope(
    private val state: IntroShowcaseState,
) {

    /**
     * Modifier that marks Compose UI element as a target for [IntroShowcase]
     */
    fun Modifier.introShowCaseTarget(
        index: Int,
        style: ShowcaseStyle = ShowcaseStyle.Default,
        introShowCaseModel: IntroShowCaseModel
    ): Modifier = introShowcaseTarget(
        state = state,
        index = index,
        style = style,
        introShowCaseModel = introShowCaseModel
    )
}