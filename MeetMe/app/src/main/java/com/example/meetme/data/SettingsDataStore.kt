package com.css545.meetme.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.time.LocalDateTime

private const val SETTINGS_PREFERENCES_NAME = "settings_preferences"

// Create a DataStore instance using the preferencesDataStore delegate, with the Context
// receiver
private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = SETTINGS_PREFERENCES_NAME)

/**
 * DataStore class for storing user preferences as Key-Value pairs
 */
class SettingsDataStore (private val context: Context){
    // TODO: This is a hack, just for the assignment. Should use a database
    private val defaultNickName = "jardiamj"
    private val defaultPhoneNumber = "5555555555"

    private object PreferenceKeys {
        val IS_TRACKING = booleanPreferencesKey("is_tracking")
        val USERNAME = stringPreferencesKey("username")
        val IS_SHARING_LOCATION = booleanPreferencesKey("is_sharing_location")
        val TRACK_LENGTH = intPreferencesKey("track_length")
        val PHONE_NUMBER = stringPreferencesKey("phone_number")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val preferencesFlow: Flow<SettingsState> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            // On the first run of the app, we will use LinearLayoutManager by default
            val name = preferences[PreferenceKeys.USERNAME] ?: defaultNickName
            val isTracking = preferences[PreferenceKeys.IS_TRACKING] ?: false
            val isSharingLocation = preferences[PreferenceKeys.IS_SHARING_LOCATION] ?: false
            val trackLength = preferences[PreferenceKeys.TRACK_LENGTH] ?: 0
            //val phoneNumber = preferences[PreferenceKeys.PHONE_NUMBER] ?: defaultPhoneNumber
            SettingsState(isTracking, isSharingLocation, name, trackLength)
        }

    suspend fun saveSharingLocationToPreferencesStore(isSharingLocation: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.IS_SHARING_LOCATION] = isSharingLocation
        }
    }

    suspend fun saveTrackingToPreferencesStore(isTracking: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.IS_TRACKING] = isTracking
        }
    }

    suspend fun saveNameToPreferencesStore(nickName: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.USERNAME] = nickName
        }
    }

    suspend fun saveTrackLengthToPreferencesStore(trackLength: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.TRACK_LENGTH] = trackLength.toInt()
        }
    }

   /* suspend fun savePhoneNumberToPreferencesStore(phoneNumber: String){
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.PHONE_NUMBER] = phoneNumber
        }
    }*/
}

data class SettingsState (
    val isTracking: Boolean = false,
    val isSharingLocation: Boolean = false,
    val username: String = "jardiamj",
    val trackLength: Int = 0,
    val phoneNumber: String = "5555555555"
)