package com.test.social_list.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.core.ui.utils.koinActivityViewModel
import com.test.social_list.SocialListScreen
import com.core.ui.composable.introshowcase.IntroShowcaseScope
import org.koin.androidx.compose.koinViewModel

const val socialListScreenRoute = "socialListScreen"

fun NavGraphBuilder.socialListScreen(
    onIntroShowCase: () -> Unit,
    showcaseScope: IntroShowcaseScope,
) {
    composable(socialListScreenRoute) {
        SocialListScreen(
            viewModel = koinViewModel(),
            mainViewModel = koinActivityViewModel(),
            onIntroShowCase,
            showcaseScope
        )
    }
}