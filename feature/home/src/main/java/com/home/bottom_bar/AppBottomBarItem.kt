@file:Suppress("unused")

package com.home.bottom_bar

import com.test.social_list.navigation.socialListScreenRoute
import com.test.core.R

enum class AppBottomBarItem(
    val icon: Int,
    val title: Int,
    val route: String,
) {
    SocialList(
        icon = R.drawable.ic_home,
        title = R.string.home,
        route = socialListScreenRoute
    ),
    DownloadList(
        icon = R.drawable.ic_downloads,
        title = R.string.downloads,
        route = "downloadScreenRoute"
    ),
    History(
        icon = R.drawable.ic_history,
        title = R.string.history,
        route = "browserHistoryScreenRoute"
    ),
    Tabs(
        icon = R.drawable.ic_tabs,
        title = R.string.tabs,
        route = "browserTabsScreenRoute"
    ),
    Setting(
        icon = R.drawable.ic_setting,
        title = R.string.settings,
        route = "settingScreenRoute"
    ),
}