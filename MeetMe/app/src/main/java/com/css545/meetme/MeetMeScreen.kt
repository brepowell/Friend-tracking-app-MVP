package com.css545.meetme

import androidx.annotation.StringRes
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.css545.meetme.ui.*
import androidx.lifecycle.viewmodel.compose.viewModel



enum class MeetMeScreen(@StringRes val title: Int) {
    Map(title = R.string.map),
    StartTracking(title = R.string.start_tracking),
    Settings(title = R.string.settings),
    Help(title = R.string.help),
    Consent(title = R.string.consent)
}

@Composable
fun MeetMeAppbar (
    currentScreen: MeetMeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar (
        title = { Text(stringResource(currentScreen.title))},
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon (
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}


@Composable
fun MeetMeApp(
    modifier: Modifier = Modifier,
    viewModel: MeetMeViewModel = viewModel()
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MeetMeScreen.valueOf(
        backStackEntry?.destination?.route ?: MeetMeScreen.Map.name
    )
    
    Scaffold (
        topBar = {
            MeetMeAppbar(
                currentScreen = currentScreen,
                // Show back arrow only if there is something on the backstack
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) {

        val settingsState = viewModel.settingsState.collectAsState()

        // This is the main screen holder. We will add the routes in a
        // NavHost composable function
        NavHost(
            navController = navController,
            startDestination =
                if (settingsState.value.isTracking) MeetMeScreen.Map.name
                else MeetMeScreen.StartTracking.name,
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
                        viewModel.isTracking(true)
                        navController.navigate(MeetMeScreen.Map.name)
                    },

                    onConsentButtonClicked = {
                        navController.navigate(MeetMeScreen.Consent.name)
                    }
                )
            }

            composable(route = MeetMeScreen.Settings.name) {
                SettingsScreen(
                    settingsState = settingsState.value,
                    onUsernameChanged = {viewModel.setUserName(it)},
                    onLocationSharingChanged = {viewModel.isSharingLocation(it)},
                    onUpdatePasswordClicked = { /* TODO: Implement */},
                    onHelpButtonClicked = { navController.navigate(MeetMeScreen.Help.name) }
                )
            }

            composable(route = MeetMeScreen.Help.name) {
                HelpScreen( onSettingsButtonClicked = {
                    navController.navigate(MeetMeScreen.Settings.name)
                })
            }

            composable(route = MeetMeScreen.Consent.name) {
                ConsentScreen()
            }
        }
    }
}