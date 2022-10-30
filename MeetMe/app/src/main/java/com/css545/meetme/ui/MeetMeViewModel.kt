package com.css545.meetme.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SettingsState (
    val isTracking: Boolean = false,
    val isSharingLocation: Boolean = false,
    val username: String = "jardiamj"
)

class MeetMeViewModel : ViewModel() {

    // TODO: add ability to save to DataStore in viewModel

    /**
     * Expose the current settings state
     */
    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState: StateFlow<SettingsState> = _settingsState.asStateFlow()

    fun isTracking(isTracking: Boolean) {
        _settingsState.update { currentState ->
            currentState.copy(
                isTracking = isTracking
            )
        }
    }

    fun isSharingLocation(isSharingLocation: Boolean) {
        _settingsState.update { currentState ->
            currentState.copy(
                isSharingLocation = isSharingLocation
            )
        }
    }

    fun setUserName(username: String) {
        _settingsState.update { currentState ->
            currentState.copy(
                username = username
            )
        }
    }
}