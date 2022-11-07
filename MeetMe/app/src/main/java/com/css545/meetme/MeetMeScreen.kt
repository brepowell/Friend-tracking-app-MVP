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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.css545.meetme.ui.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.css545.meetme.data.SettingsDataStore
import androidx.lifecycle.*
import com.css545.meetme.data.SettingsState
import kotlinx.coroutines.launch


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
    onSettingsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }


            }
        }
    )
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
                    // This is where the Track button should take us
                    onTrackButtonClicked = {
                        scope.launch {
                            settingsDataStore.saveTrackingToPreferencesStore(false)
                        }
                        navController.navigate(MeetMeScreen.StartTracking.name)
                    }
                )
            }

            composable(route = MeetMeScreen.StartTracking.name) {
                StartTrackingScreen(
                    settingsState = settingsState.value,
//                    onStartTrackingButtonClicked = {

//                        navController.navigate(MeetMeScreen.Map.name)
//                    },
                    onStartTrackingButtonClicked = {
                        scope.launch {
                            settingsDataStore.saveTrackLengthToPreferencesStore(it)
//                            settingsDataStore.saveTrackingToPreferencesStore(true)
                        }
                        navController.navigate(MeetMeScreen.Consent.name)
                    }
                )
            }

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
                    onHelpButtonClicked = { navController.navigate(MeetMeScreen.Help.name) }
                )
            }

            composable(route = MeetMeScreen.Help.name) {
                HelpScreen( onSettingsButtonClicked = {
                    navController.navigate(MeetMeScreen.Settings.name)
                })
            }

            composable(route = MeetMeScreen.Consent.name) {
                ConsentScreen(
                    settingsState = settingsState.value,
                    onYesClicked = {
                        scope.launch {
                            settingsDataStore.saveTrackingToPreferencesStore(true)
                        }
                        navController.navigate(MeetMeScreen.Map.name)
                   },
                    onNoClicked = { navController.navigate(MeetMeScreen.StartTracking.name) }
                )
            }
        }
    }
}