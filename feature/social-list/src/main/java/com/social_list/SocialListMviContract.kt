package com.social_list

import com.mvi.ScreenEvent
import com.mvi.ScreenSingleEvent
import com.mvi.ScreenState

class SocialListState : ScreenState

sealed interface SocialListEvent : ScreenEvent {
    data object OnBoardingSocialListEvent : SocialListEvent
}

sealed interface SocialListSingleEvent : ScreenSingleEvent {

    data object IntroShowcaseSocialSingleEvent : SocialListSingleEvent
}