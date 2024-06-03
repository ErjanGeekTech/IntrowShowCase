package com.core.ui.model

import androidx.annotation.StringRes
import com.core.R

data class IntroShowCaseModel(
    @StringRes
    val title: Int,
    val arrowShowCaseType: ArrowShowCaseType,
    @StringRes
    val titleAction: Int = R.string.next
)