package com.css545.meetme

import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.css545.meetme.ui.*


enum class MeetMeScreen() {
    Map,
    StartTracking,
    Settings,
    Help,
    Consent
}

@Composable
fun MeetMeAppbar (
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar (
        title = { Text(stringResource(id = R.string.app_name))},
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon (
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            }
        }
    )
}


@Composable
fun MeetMeApp(modifier: Modifier = Modifier) {
    // TODO: Create NavController
    val navController = rememberNavController()
    
    //TODO: Get current back stack entry
    
    //TODO: Get the name of the current screen
    
    Scaffold (
        topBar = {
            MeetMeAppbar(canNavigateBack = false, navigateUp = { /*TODO: implement*/ })
        }
    ) {
        // TODO: add ability to get properties from a viewModel and DataStore

        // This is the main screen holder. We will add the routes in a
        // NavHost composable function

        // TODO: add NavHost
        NavHost(
            navController = navController,
            startDestination = MeetMeScreen.Map.name,
            modifier = modifier.padding(10.dp)
        ) {
            // We will create composable routes for the various screens
            composable(route = MeetMeScreen.Map.name) {
                MapScreen(
                    // This is where the Settings button should take us
                    onSettingsButtonClicked = {
                        navController.navigate(MeetMeScreen.Settings.name)
                    },
                    // This is where the Track button should take us
                    onTrackButtonClicked = {
                        navController.navigate(MeetMeScreen.StartTracking.name)
                    }
                )
            }

            composable(route = MeetMeScreen.StartTracking.name) {
                StartTrackingScreen(
                    onStartTrackingButtonClicked = {
                        navController.navigate(MeetMeScreen.Map.name)
                    },

                    onConsentButtonClicked = {
                        navController.navigate(MeetMeScreen.Consent.name)
                    }
                )
            }

            composable(route = MeetMeScreen.Settings.name) {
                SettingsScreen()
            }

            composable(route = MeetMeScreen.Help.name) {
                HelpScreen()
            }

            composable(route = MeetMeScreen.Consent.name) {
                ConsentScreen()
            }
        }
    }
}