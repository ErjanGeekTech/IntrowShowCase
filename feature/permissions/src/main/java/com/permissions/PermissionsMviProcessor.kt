package com.permissions

import androidx.lifecycle.SavedStateHandle
import com.mvi.MviProcessor
import com.test.core.R

class PermissionsViewModel(
    savedStateHandle: SavedStateHandle,
) : MviProcessor<PermissionsState, PermissionsEvent, PermissionsSingleEvent>() {

    private val data = savedStateHandle.get<String>("data").let {
        when (it) {
            "true" -> true
            else -> false
        }
    }

    override fun initialState(): PermissionsState {
        return firstData()
    }

    override fun reduce(event: PermissionsEvent, state: PermissionsState): PermissionsState {
        return when (event) {
            PermissionsEvent.Next -> state
            PermissionsEvent.Start -> state
            PermissionsEvent.Skip -> state
        }
    }

    override suspend fun handleEvent(event: PermissionsEvent, state: PermissionsState): PermissionsEvent? {
        when (event) {
            PermissionsEvent.Next -> end()
            PermissionsEvent.Start -> end()
            PermissionsEvent.Skip -> end()
        }

        return null
    }

    private fun firstData(): PermissionsState {
        return PermissionsState(
            image = R.drawable.img_onb_notifi_perm,
            text = R.string.text_onb_notifi,
            subtext = R.string.subtext_onb_notifi,
            buttonText = R.string.enable_notifications,
            actionEvent = PermissionsEvent.Next,
        )
    }

    private fun end() {
        triggerSingleEvent(PermissionsSingleEvent.NavigateToHome)
    }
}