package com.example.meetme

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.css545.meetme.MeetMeScreen
import com.css545.meetme.ui.*
import kotlinx.coroutines.launch
import androidx.navigation.compose.NavHost
import com.css545.meetme.data.SettingsDataStore
import com.css545.meetme.data.SettingsState

@Composable
fun NavigationRoutes (
    navController: NavHostController,
    startScreen: String,
    modifier: Modifier
) {
    /** We use a DataStore for data that needs to survive app termination */
    val settingsDataStore = SettingsDataStore(LocalContext.current)
    val settingsState = settingsDataStore.preferencesFlow.collectAsState(initial = SettingsState())
    val scope = rememberCoroutineScope()

    /** ------------------------------- THE NAV HOST --------------------------------- */
    /** This is the main screen holder.
     * We will add all of the routes in a NavHost composable function */
    NavHost(
    navController = navController,
    startDestination = startScreen,
    modifier = modifier.padding(10.dp)
    ) {

        /** THE TRACKING START SCREEN NAVIGATION (mapped to a composable):
         * This is where the user chooses the friend they would like to track
         * and the duration of the tracking session
         * After inputting those fields, the user can send an invitation to be tracked
         * to the selected friend
         * */
        composable(route = MeetMeScreen.TrackingStart.name) {
            StartTrackingScreen(
                settingsState = settingsState.value,
                onStartTrackingButtonClicked = {
                    scope.launch {
                        /** SAVE THE USER INPUT FOR THE TRACKING DURATION */
                        /** SAVE THE USER INPUT FOR THE TRACKING DURATION */
                        settingsDataStore.saveTrackLengthToPreferencesStore(it)
//                            settingsDataStore.saveTrackingToPreferencesStore(true)
                    }

                    scope.launch {
                        /** SAVE THE USER INPUT FOR THE FRIEND'S PHONE NUMBER */
                        /** SAVE THE USER INPUT FOR THE FRIEND'S PHONE NUMBER */
                        settingsDataStore.savePhoneNumberToPreferencesStore(it)
                    }

                    /** NAVIGATE TO WAITING SCREEN */

                    /** NAVIGATE TO WAITING SCREEN */
                    navController.navigate(MeetMeScreen.Waiting.name)
                }
            )
        }

        /** THE WAITING FOR CONSENT SCREEN NAVIGATION (mapped to a composable):
         * The invitation is sent to the friend.
         * The user waits for confirmation
         * They have the option to cancel the request if their friend is unresponsive
         * */
        composable(route = MeetMeScreen.Waiting.name){
            WaitingForConsentScreen(
                /** OPTION TO CANCEL THE INVITATION THAT WAS SENT */
                /** OPTION TO CANCEL THE INVITATION THAT WAS SENT */
                onCancelButtonClicked = {
                    navController.navigate(MeetMeScreen.TrackingStart.name)
                },

                // TODO: WE WILL WANT TO POP THIS SCREEN OFF OF THE BACK STACK
                //  BECAUSE THE USER SHOULD NOT RETURN TO IT.

                // TODO: THIS BUTTON WILL NOT BE NECESSARY WITH THE FINAL PRODUCT
                //  REMOVE WHEN DONE
                onContinueButtonClicked = {
                    navController.navigate(MeetMeScreen.Consent.name)
                }
                // TODO: THE WAITING SCREEN SHOULD AUTOMATICALLY NAVIGATE TO THE MAP
                //  SCREEN IF CONSENT WAS GIVEN
            )
        }


        /** THE CONSENT SCREEN NAVIGATION (mapped to a composable)
         * Here, the user sees that someone has invited them to a tracking session
         * They can either consent to being tracked and then go to the maps screen
         * or they can decline and return to the start tracking screen.
         * */
        composable(route = MeetMeScreen.Consent.name) {
            ConsentScreen(
                settingsState = settingsState.value,

                // TODO: WE WILL WANT TO POP THIS SCREEN OFF OF THE BACK STACK
                //  BECAUSE THE USER SHOULD NOT RETURN TO IT.

                onYesClicked = {
                    scope.launch {
                        /** THE USER HAS BEEN INVITED TO BE TRACKED AND CONSENTS */
                        settingsDataStore.saveTrackingToPreferencesStore(true)
                    }

                    /** NAVIGATE TO MAP SCREEN TO START TRACKING */
                    navController.navigate(MeetMeScreen.Map.name)
                },
                /** NAVIGATE BACK TO START TRACKING SCREEN */
                /** NAVIGATE BACK TO START TRACKING SCREEN */
                onNoClicked = {

                    /* TODO: WE MAY NEED TO IMPLEMENT A REPLY MESSAGE HERE
                        THAT GOES BACK TO THE PERSON WHO INVITED THEM
                        SAYING THAT CONSENT WAS DECLINED */
                    navController.navigate(MeetMeScreen.TrackingStart.name)
                }
            )
        }

        /** THE MAP SCREEN NAVIGATION (mapped to a composable)
         * Here, the user sees a map with a pin for their location and a pin for
         * their friend's location.
         * They can choose to stop the session at any time.
         * */
        composable(route = MeetMeScreen.Map.name) {
            MapScreen(
                onStopTrackButtonClicked = {
                    /** NAVIGATE BACK TO STOP TRACKING SCREEN */
                    navController.navigate(MeetMeScreen.TrackingEnd.name)
                }
                /* TODO: ASK FOR PERMISSIONS TO TRACK FINE LOCATION*/
            )
        }

        composable(route = MeetMeScreen.TrackingEnd.name){
            StopTrackingScreen(
                onYesClicked = {
                    scope.launch {
                        /** THE USER STOPPED THE TRACKING SESSION -- THE SESSION IS OVER */
                        settingsDataStore.saveTrackingToPreferencesStore(false)
                    }
                    /** NAVIGATE TO THE START TRACKING SCREEN */
                    navController.navigate(MeetMeScreen.TrackingStart.name)
                },
                onNoClicked = {
                    /** RETURN BACK TO THE MAP SCREEN */
                    navController.navigate(MeetMeScreen.Map.name)
                }

            )
        }

        /** THE SETTINGS SCREEN NAVIGATION (mapped to a composable)
         * This is where the user can choose to edit their preferences
         * and user information
         * */
        composable(route = MeetMeScreen.Settings.name) {
            SettingsScreen(
                settingsState = settingsState.value,
                onUsernameChanged = {
                    // TODO: This is a hack. Username should not be saved every time the
                    //  textbox changes. Perhaps use a ViewModel or LiveData.
                    //  Perhaps this logic should be part of the SettingsScreen
                    scope.launch {
                        settingsDataStore.saveNameToPreferencesStore(it)
                    }
                },
                onLocationSharingChanged = {
                    scope.launch {
                        settingsDataStore.saveSharingLocationToPreferencesStore(it)
                    }
                },
                onUpdatePasswordClicked = { /* TODO: Implement */},

                /** NAVIGATE TO HELP SCREEN */

                /** NAVIGATE TO HELP SCREEN */
                onHelpButtonClicked = { navController.navigate(MeetMeScreen.Help.name) }
            )
        }

        /** THE HELP SCREEN NAVIGATION (mapped to a composable)
         * This is where the user can learn more about the app and ask for help
         * */
        composable(route = MeetMeScreen.Help.name) {
            HelpScreen( onSettingsButtonClicked = {
                /** NAVIGATE TO SETTINGS SCREEN */
                /** NAVIGATE TO SETTINGS SCREEN */
                navController.navigate(MeetMeScreen.Settings.name)
            })
        }
    }
}