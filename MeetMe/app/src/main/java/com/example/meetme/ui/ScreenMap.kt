package com.css545.meetme.ui


import android.content.Intent
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.css545.meetme.R
import com.css545.meetme.ui.components.CustomButton
import com.example.meetme.ui.LocationViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
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
        var friend = locationViewModel.latLng2
        GoogleMapView(location.value,friend.value)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            /** STOP TRACKING BUTTON */
            CustomButton(
                onClick = { onStopTrackButtonClicked() },
                text = stringResource(id = R.string.screen_title_tracking_stop)
            )
        }
    }
}

@Composable
fun GoogleMapView (
    location: LatLng, friendLocation:LatLng
) {
    val labelState = MarkerState(position = location)
    val friendState = MarkerState(position = friendLocation)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = labelState,
            title = "You",
            snippet = "User Location"
        )
        Marker(
            state = friendState,
            title = "Friend",
            snippet = "Friend Location",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ShowMapPreview()
{
    MapScreen(onStopTrackButtonClicked = {})

}