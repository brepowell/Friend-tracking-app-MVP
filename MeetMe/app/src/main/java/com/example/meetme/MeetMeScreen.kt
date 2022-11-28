package com.css545.meetme


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
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
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.css545.meetme.data.SettingsDataStore
import com.css545.meetme.data.SettingsState
import com.css545.meetme.ui.*
import kotlinx.coroutines.delay
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
    intent: Intent,
    context: Context,
    modifier: Modifier = Modifier
//    viewModel: MeetMeViewModel = viewModel()
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currRoute = backStackEntry?.destination?.route ?: MeetMeScreen.Map.name
    val screenName = currRoute.split("/")[0] // remove any parameters from the route
    val currentScreen = MeetMeScreen.valueOf(screenName)

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

        /** We use a DataStore for data that needs to survive app termination */
        val settingsDataStore = SettingsDataStore(LocalContext.current)
        val settingsState = settingsDataStore.preferencesFlow.collectAsState(initial = SettingsState())
        val scope = rememberCoroutineScope()

        /** ------------------------------ THE NAVIGATION CONTROLLER -----------------------------*/
        // Select the starting screen depending on the current state.
        val startScreen = if (settingsState.value.isTracking) MeetMeScreen.Map.name
                          else MeetMeScreen.TrackingStart.name

        /** ------------------------------- THE NAV HOST --------------------------------- */
        /** This is the main screen holder.
         * We will add all of the routes in a NavHost composable function */
        NavHost(
            navController = navController,
            startDestination = startScreen,
            modifier = modifier.padding(10.dp)
        ) {

            val uri = "https://www.meetme.com"

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

/*                        scope.launch {
                            *//** SAVE THE USER INPUT FOR THE FRIEND'S PHONE NUMBER *//*
                            settingsDataStore.savePhoneNumberToPreferencesStore(it)
                        }
*/
                        scope.launch {
                            delay(2000)

                            /** SEND AN SMS MESSAGE WITH A DEEPLINK TO THE CONSENT PAGE FOR USER 2 **/
                            val session = "1234" // TODO: CREATE A UNIQUE SESSION ID NUMBER
                            val linkToMeetMe =
                                "$uri/Consent/$session" //Need to figure out a unique link?
                            val hours = settingsState.value.trackLength.toString()
                            val res = context.resources
                            val message =
                                res.getString(
                                    R.string.tracking_handshake_initial_message,
                                    hours,
                                    linkToMeetMe
                                )
                            //"Please join me in a tracking session for $hours hours on MeetMe $linkToMeetMe"
                            //Test phone 1 is 6505551212
                            //Test phone 2 is 16505556789
                            // TODO: REMOVE THE PHONE NUMBER FROM THIS PART AND HAVE A MESSAGE
                            //  ABOUT WHAT SHOULD HAPPEN
                            //val phoneNumber = "6505551212" //RECIPIENT'S PHONE NUMBER
                            sendIntent(context, message)

                            /** NAVIGATE TO WAITING SCREEN */
                            delay(2000)
                            navController.navigate(MeetMeScreen.Waiting.name)
                        }
                    },

                    // TODO: REMOVE WHEN FINISHED TESTING 2 LOCATIONS
                    onMapButtonClicked = {
                        navController.navigate(MeetMeScreen.Map.name)
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

                    // TODO: DO WE NEED TO POP THIS SCREEN OFF OF THE BACK STACK
                    //  BECAUSE THE USER SHOULD NOT RETURN TO IT?
                )
            }


            /** THE CONSENT SCREEN NAVIGATION (mapped to a composable)
             * Here, the user sees that someone has invited them to a tracking session
             * They can either consent to being tracked and then go to the maps screen
             * or they can decline and return to the start tracking screen.
             *
             * The deepLinks parameter will allow another app (an SMS app) to navigate here
             * from a deep link.
             * */
            composable(route = MeetMeScreen.Consent.name,
                        arguments = listOf(navArgument("sessionID") { type = NavType.IntType}),
                        deepLinks = listOf(navDeepLink { uriPattern = "$uri/Consent/{sessionID}" })
            ) {
                // Get the sessionID from the URI. URI example: https://www.meetme.com/Consent/1234
                val sessionID = backStackEntry?.arguments?.getInt("sessionID") ?: 0

                ConsentScreen(
                    sessionID = sessionID,
                    settingsState = settingsState.value,

                    // TODO: WE WILL WANT TO POP THIS SCREEN OFF OF THE BACK STACK
                    //  BECAUSE THE USER SHOULD NOT RETURN TO IT.

                    onYesClicked = {
                        scope.launch {
                            /** THE USER HAS BEEN INVITED TO BE TRACKED AND CONSENTS */
                            settingsDataStore.saveTrackingToPreferencesStore(true)
                        }

                        /** SEND AN SMS MESSAGE WITH A DEEPLINK TO THE MAPS PAGE FOR BOTH USERS **/
                        scope.launch { //If I put this not inside the co-routine it takes a few seconds
                            val linkToMeetMe = "$uri/Map/$sessionID"
                            val res = context.resources
                            val message =
                                res.getString(R.string.tracking_handshake_consent_yes, linkToMeetMe)
                                //"Yes, I will join you on MeetMe. Let's go to the map! $linkToMeetMe"
                            sendIntent(context, message)
                        }


                        /** NAVIGATE TO MAP SCREEN */
                        navController.navigate(MeetMeScreen.Map.name)
                    },

                    /** NAVIGATE BACK TO START TRACKING SCREEN */
                    onNoClicked = {

                        /** SEND A REPLY SMS MESSAGE TO USER 1 SAYING CONSENT IS DENIED **/
                        val linkToMeetMe = "$uri/TrackingStart"
                        val res = context.resources
                        val message =
                            res.getString(R.string.tracking_handshake_consent_no, linkToMeetMe)
                        //"No, thank you. I cannot join you right now. %s"
                        sendIntent(context, message)
                        navController.navigate(MeetMeScreen.TrackingStart.name)
                    }
                )
            }

            /** THE MAP SCREEN NAVIGATION (mapped to a composable)
             * Here, the user sees a map with a pin for their location and a pin for
             * their friend's location.
             * They can choose to stop the session at any time.
             *
             * The deepLinks parameter will allow another app (an SMS app) to navigate here
             * from a deep link.
             * */
            composable(route = MeetMeScreen.Map.name,
                arguments = listOf(navArgument("sessionID") { type = NavType.IntType ; defaultValue=123}),
                deepLinks = listOf(navDeepLink { uriPattern = "$uri/Map/{sessionID}" })
            ) {
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

                        //TODO: END THE SESSION SOMEHOW - IN THE DATABASE

                        scope.launch {
                            /** THE USER STOPPED THE TRACKING SESSION -- THE SESSION IS OVER */
                            settingsDataStore.saveTrackingToPreferencesStore(false)
                        }
                        /** NAVIGATE TO THE START TRACKING SCREEN */
                        navController.navigate(MeetMeScreen.TrackingStart.name)
                    },
                    onNoClicked = {
                        /** RETURN BACK TO THE MAP SCREEN */
                        //navController.navigate(MeetMeScreen.Map.name)
                        val goTo = Uri.parse("$uri/Map/1234")
                        val intentToMap = Intent(Intent.ACTION_VIEW, goTo)
                        context.startActivity(intentToMap, null)
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

fun sendIntent(context: Context, message: String){
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND  //Limit to the SMS apps
        putExtra(Intent.EXTRA_TEXT, message) //Works
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION //Works
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(context, shareIntent, null)

    // TODO: ADD RESOLVEACTIVITY() TO MAKE SURE THERE IS AN APP
    //  THAT CAN HANDLE THE REQUEST ???
}
