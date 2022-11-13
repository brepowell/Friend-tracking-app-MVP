package com.css545.meetme

import android.content.Intent
import android.content.Intent.getIntent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.css545.meetme.ui.*
import com.css545.meetme.data.SettingsDataStore
import com.css545.meetme.data.SettingsState
import com.example.meetme.NavigationRoutes
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
//        val scope = rememberCoroutineScope()


        /** ------------------------------ THE NAVIGATION CONTROLLER -----------------------------*/
        // We first get the Uri from the intent. If we got here from a deep linking intent we will
        // have a non-null uri that we can use to navigate to the Consent screen
        val uri: Uri? = intent.data
        val deepLinkMsg = remember { mutableStateOf("") }
        if (uri != null) {
            val parameters: List<String> = uri.pathSegments
            val param = parameters[parameters.size - 1]
            deepLinkMsg.value = param
        }
        val startScreen = if (uri != null) deepLinkMsg.value
        else if (settingsState.value.isTracking) MeetMeScreen.Map.name
        else MeetMeScreen.TrackingStart.name
        NavigationRoutes(navController = navController, startScreen = startScreen, modifier = modifier)
    }
}