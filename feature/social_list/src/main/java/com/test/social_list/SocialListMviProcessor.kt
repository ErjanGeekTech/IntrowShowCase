package com.test.social_list

import com.mvi.MviProcessor
import com.test.social_list.SocialListEvent
import com.test.social_list.SocialListSingleEvent
import com.test.social_list.SocialListState

class SocialListViewModel :
    MviProcessor<SocialListState, SocialListEvent, SocialListSingleEvent>() {

    override fun initialState(): SocialListState {
        return SocialListState()
    }

    override fun reduce(event: SocialListEvent, state: SocialListState): SocialListState {
        return state
    }

    override suspend fun handleEvent(
        event: SocialListEvent,
        state: SocialListState
    ): SocialListEvent? {
        when (event) {
            SocialListEvent.OnBoardingSocialListEvent -> triggerSingleEvent(SocialListSingleEvent.IntroShowcaseSocialSingleEvent)
        }
        return null
    }
}