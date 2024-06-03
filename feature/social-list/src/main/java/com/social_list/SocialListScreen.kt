package com.social_list

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.core.R
import com.core.ui.composable.LifecycleListener
import com.core.ui.utils.getActivity
import com.mvi.main_mvi.MainEvent
import com.mvi.main_mvi.MainViewModel
import com.social_list.composable.ScreenV3V4
import com.core.ui.composable.introshowcase.IntroShowcaseScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
internal fun SocialListScreen(
    viewModel: SocialListViewModel,
    mainViewModel: MainViewModel,
    onIntroShowCase: () -> Unit,
    showcaseScope: IntroShowcaseScope
) {
    val activity = getActivity()
    val context = LocalContext.current

    var onBackCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(
        key1 = Unit,
        block = {
            viewModel.singleEvent.onEach {
                onIntroShowCase.invoke()
            }.collect()
        }
    )

    ScreenV3V4(showcaseScope) {
        viewModel.sendEvent(it)
    }

    LifecycleListener(
        onStart = { onBackCount = 0 },
    )

    BackHandler(enabled = onBackCount < 1) {
        mainViewModel.sendEvent(
            MainEvent.RateUp(activity = activity) {
                ++onBackCount
                Toast.makeText(
                    context,
                    context.getString(R.string.press_again_to_exit),
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }
}