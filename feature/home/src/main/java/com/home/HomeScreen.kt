package com.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.core.ui.utils.isLandscape
import com.home.bottom_bar.AppBottomBar
import com.home.bottom_bar.AppBottomBarItem
import com.home.nav_graph.homeNavGraph
import com.core.ui.composable.introshowcase.IntroShowcase
import com.core.ui.composable.introshowcase.components.rememberIntroShowcaseState

@Composable
internal fun HomeScreen(parentNavController: NavController) {
    Screen(parentNavController)
}

@Composable
private fun Screen(parentNavController: NavController) {
    val navController: NavHostController = rememberNavController()

    var isShowcasing by remember {
        mutableStateOf(false)
    }

    val introShowcaseState = rememberIntroShowcaseState()

    fun NavHostController.bottomBarClick(item: AppBottomBarItem, currentRoute: String?) {
        if (item.route == currentRoute) return

        try {
            navigate(item.route) {
                popUpTo(AppBottomBarItem.SocialList.route)

                launchSingleTop = true
                restoreState = true
            }
        } catch (ignore: Exception) {
        }
    }

    IntroShowcase(
        showIntroShowCase = isShowcasing,
        state = introShowcaseState,
        onShowCaseCompleted = { isShowcasing = false }
    ) {
        Scaffold(
            bottomBar = {
                AppBottomBar(
                    navController = navController,
                    onClick = navController::bottomBarClick,
                    this@IntroShowcase
                )
            },
            modifier = Modifier
                .fillMaxSize()

        ) { paddingValues ->
            val paddingBottom =
                if (isLandscape()) 0.dp
                else paddingValues.calculateBottomPadding()

            NavHost(
                navController = navController,
                startDestination = AppBottomBarItem.SocialList.route,
                builder = {
                    homeNavGraph(
                        navController = navController,
                        parentNavController = parentNavController,
                        onBoardingInfoClick = {
                            isShowcasing = true
                            introShowcaseState.reset()
                        },
                        this@IntroShowcase
                    )
                },
                modifier = Modifier
                    .padding(bottom = paddingBottom)
            )

        }
    }
}