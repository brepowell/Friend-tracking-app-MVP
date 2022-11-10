package com.css545.meetme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.css545.meetme.ui.*
import com.css545.meetme.data.SettingsDataStore
import com.css545.meetme.data.SettingsState
import kotlinx.coroutines.launch

/** DEFINING ROUTES (A route is "a string that corresponds to a destination"):
    TrackingStart:  The screen where the user chooses the number of hours and sends an invitation
    Waiting:        After the user has send an invitation, this screen shows
    Consent:        After an invitation is received, this screen shows, asking for consent
    Map:            The map shows during tracking. Displays pins for all users
    Settings:       User preferences are here
    Help:           User help is here
"Enum classes in Kotlin have a name property that returns a string with the property name."
 */
enum class MeetMeScreen(@StringRes val title: Int) {
    TrackingStart(title = R.string.screen_title_tracking_start),
    Waiting(title = R.string.screen_title_waiting_for_consent),
    Consent(title = R.string.screen_title_consent),
    Map(title = R.string.screen_title_map),
    TrackingEnd(title = R.string.screen_title_tracking_stop),
    Settings(title = R.string.screen_title_settings),
    Help(title = R.string.screen_title_help),
}

/** NAVIGATION BAR ALONG THE TOP
 * This has a back button, the title of the page, and a settings button
 * */
@Composable
fun MeetMeAppbar (
    currentScreen: MeetMeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    onSettingsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    /** THE TOP APP BAR - TITLE AND BACK BUTTON */
    TopAppBar (
        title = { Text(stringResource(currentScreen.title))},
        modifier = modifier,
        navigationIcon = {
            Row (
                horizontalArrangement = Arrangement.End
            ){
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon (
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.button_back)
                        )
                    }
                }


            }
        }
    )
    /** THE TOP APP BAR - THE SETTINGS GEAR ICON */
    Row (
        horizontalArrangement = Arrangement.End
    ){
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = { onSettingsClicked() }
        ) {
            Icon(imageVector = Icons.Filled.Settings, contentDescription = null, tint = Color.White)
        }
    }

}

/** MEET ME APP -- ALL NAVIGATION HAPPENS HERE */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MeetMeApp(
    modifier: Modifier = Modifier
//    viewModel: MeetMeViewModel = viewModel()
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MeetMeScreen.valueOf(
        backStackEntry?.destination?.route ?: MeetMeScreen.Map.name
    )

    /** ------------------------------- THE APP BAR --------------------------------- */
    Scaffold (
        topBar = {
            MeetMeAppbar(
                currentScreen = currentScreen,
                // Show back arrow only if there is something on the backstack
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                onSettingsClicked = { navController.navigate(MeetMeScreen.Settings.name) }
            )
        }
    ) {

//        val settingsState = viewModel.settingsState.collectAsState()

        val settingsDataStore = SettingsDataStore(LocalContext.current)
        val settingsState = settingsDataStore.preferencesFlow.collectAsState(initial = SettingsState())
        val scope = rememberCoroutineScope()


        /** ------------------------------- THE NAV HOST --------------------------------- */
        /** This is the main screen holder.
         * We will add all of the routes in a NavHost composable function */
        NavHost(
            navController = navController,
            startDestination =
                if (settingsState.value.isTracking) MeetMeScreen.Map.name
                else MeetMeScreen.TrackingStart.name,
            modifier = modifier.padding(10.dp)
        ) {
            // We will create composable routes for the various screens

            /** THE TRACKING START SCREEN NAVIGATION (mapped to a composable):
             * This is where the user chooses the friend they would like to track
             * and the duration of the tracking session
             * After inputting those fields, the user can send an invitation to be tracked
             * to the selected friend
             * */
            composable(route = MeetMeScreen.TrackingStart.name) {
                StartTrackingScreen(
                    settingsState = settingsState.value,
//                    onStartTrackingButtonClicked = {

//                        navController.navigate(MeetMeScreen.Map.name)
//                    },
                    onStartTrackingButtonClicked = {
                        scope.launch {
                            /** SAVE THE USER INPUT FOR THE TRACKING DURATION */
                            settingsDataStore.saveTrackLengthToPreferencesStore(it)
//                            settingsDataStore.saveTrackingToPreferencesStore(true)
                        }

                        /** NAVIGATE TO CONSENT SCREEN */
                        // TODO: THIS SHOULD NAVIGATE TO THE WAITING SCREEN LATER, NOT CONSENT
                        navController.navigate(MeetMeScreen.Consent.name)
                    }
                )
            }

            // TODO: ADD A WAITING SCREEN
            // TODO: THE WAITING SCREEN SHOULD NAVIGATE TO THE MAP SCREEN IF CONSENT WAS GIVEN
            // TODO: THERE SHOULD BE AN OPTION TO CANCEL THE INVITATION THAT WAS SENT

            /** THE CONSENT SCREEN NAVIGATION (mapped to a composable)
             * Here, the user sees that someone has invited them to a tracking session
             * They can either consent to being tracked and then go to the maps screen
             * or they can decline and return to the start tracking screen.
             * */
            composable(route = MeetMeScreen.Consent.name) {
                ConsentScreen(
                    settingsState = settingsState.value,
                    onYesClicked = {
                        scope.launch {
                            /** THE USER HAS BEEN INVITED TO BE TRACKED AND CONSENTS */
                            settingsDataStore.saveTrackingToPreferencesStore(true)
                        }

                        /** NAVIGATE TO MAP SCREEN TO START TRACKING */
                        navController.navigate(MeetMeScreen.Map.name)

                        /* TODO: ASK FOR PERMISSIONS TO TRACK FINE LOCATION*/
                    },
                    /** NAVIGATE BACK TO START TRACKING SCREEN */
                    onNoClicked = {
                        /* TODO: WE MAY NEED TO IMPLEMENT A REPLY MESSAGE HERE
                            SAYING THAT CONSENT WAS DECLINED*/
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
                    },//{viewModel.setUserName(it)},
                    onLocationSharingChanged = {
                        scope.launch {
                            settingsDataStore.saveSharingLocationToPreferencesStore(it)
                        }
                    },
                    onUpdatePasswordClicked = { /* TODO: Implement */},

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
                    navController.navigate(MeetMeScreen.Settings.name)
                })
            }

        }
    }
}