package com.css545.meetme.ui


import android.content.Intent
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.css545.meetme.ConsentActivity
import com.css545.meetme.R
import com.css545.meetme.ui.components.CustomButton
import com.example.meetme.ui.LocationViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapScreen(onStopTrackButtonClicked: () -> Unit,
              locationViewModel: LocationViewModel = LocationViewModel(LocalContext.current)
) {

    Box{

        /**
         * The locationViewModel uses a LocationManager to retrieve the current user location.
         * the location is a MutableState variable, that way the map is recompose every time the
         * location updates.
         * */
        locationViewModel.getLocation()
        var location = locationViewModel.latLng
        GoogleMapView(location.value)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomButton(
                onClick = { onStopTrackButtonClicked() },
                text = stringResource(id = R.string.screen_title_tracking_stop)
            )
            val context = LocalContext.current
            CustomButton(
                onClick = {
                    context.startActivity(Intent(context, ConsentActivity::class.java), null)
                },
                text = "Go to Consent Activity"
            )
        }
    }
}

@Composable
fun GoogleMapView (
    location: LatLng
) {
    val labelState = MarkerState(position = location)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = labelState,
            title = "Singapore",
            snippet = "Marker in Singapore"
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ShowMapPreview()
{
    MapScreen(onStopTrackButtonClicked = {})

}