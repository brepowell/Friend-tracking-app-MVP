package com.css545.meetme.ui

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import com.css545.meetme.data.SettingsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val LAYOUT_PREFERENCES_NAME = "layout_preferences"

// Create a DataStore instance using the preferencesDataStore delegate, with the Context
// receiver
private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = LAYOUT_PREFERENCES_NAME)

class MeetMeViewModel() : ViewModel() {

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
