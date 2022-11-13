package com.css545.meetme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.css545.meetme.data.SettingsDataStore
import com.css545.meetme.data.SettingsState
import com.css545.meetme.ui.*
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
fun MeetMeAppStart(
    modifier: Modifier = Modifier
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
            startDestination = MeetMeScreen.TrackingStart.name,
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
                    onStartTrackingButtonClicked = {
                        scope.launch {
                            /** SAVE THE USER INPUT FOR THE TRACKING DURATION */
                            settingsDataStore.saveTrackLengthToPreferencesStore(it)
                        }

                        scope.launch {
                            /** SAVE THE USER INPUT FOR THE FRIEND'S PHONE NUMBER */
                            settingsDataStore.savePhoneNumberToPreferencesStore(it)
                        }
                        // TODO: SENT INTENT TO SMS

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
                    onCancelButtonClicked = {
                        navController.navigate(MeetMeScreen.TrackingStart.name)
                    },

                    //An IMPLICIT INTENT -- has no "component"
                    // -- the first parameter is an action, not a component
                    //val intent = Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phoneNumber , null))
                    //putExtra (without an 's') is a key value pair that gives extra information.
                    //another option is to make a Bundle object and insert the Bundle in the Intent
                    //with putExtras() -- with an 's'
                    //intent.putExtra("sms_body", "Hello Breanna. From Breanna")
                    //startActivity(intent)
                    //val navigate = Intent(this@MainActivity, MapsActivityLauncher::class.java)

                    // TODO: WE WILL WANT TO POP THIS SCREEN OFF OF THE BACK STACK
                    //  BECAUSE THE USER SHOULD NOT RETURN TO IT.

                    // TODO: THIS BUTTON WILL NOT BE NECESSARY WITH THE FINAL PRODUCT
                    //  REMOVE WHEN DONE
                    onContinueButtonClicked = {

                        /** MOVE FROM THE WAITING SCREEN TO THE CONSENT SCREEN*/
                        val moveToAnotherActivity = MainActivity()
                        moveToAnotherActivity.consentIntent()

                    }
                    // TODO: THE WAITING SCREEN SHOULD AUTOMATICALLY NAVIGATE TO THE MAP
                    //  SCREEN IF CONSENT WAS GIVEN
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

@Composable
fun MeetMeAppConsent(
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
            startDestination = MeetMeScreen.Consent.name,
            modifier = modifier.padding(10.dp)
        ) {
            // We will create composable routes for the various screens

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

                        /** Launch the Map */
                        val moveToAnotherActivity = ConsentActivityLauncher()
                        moveToAnotherActivity.startMapIntent()

                    },
                    /** NAVIGATE BACK TO START TRACKING SCREEN */
                    onNoClicked = {

                        /* TODO: WE MAY NEED TO IMPLEMENT A REPLY MESSAGE HERE
                            THAT GOES BACK TO THE PERSON WHO INVITED THEM
                            SAYING THAT CONSENT WAS DECLINED */
                        navController.navigate(MeetMeScreen.TrackingStart.name)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MeetMeAppMap(
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
            startDestination = MeetMeScreen.Map.name,
            modifier = modifier.padding(10.dp)
        ) {

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

                        /** STOP THE MAP AND RETURN TO THE MAIN ACTIVITY */
                        val moveToAnotherActivity = MapsActivityLauncher()
                        moveToAnotherActivity.stopMapIntent()

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