package com.css545.meetme.ui


import android.content.Intent
import android.location.Location
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import androidx.lifecycle.viewmodel.compose.viewModel

import android.location.LocationListener;
import android.location.LocationManager;
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.asStateFlow


@Composable
fun MapScreen(onStopTrackButtonClicked: () -> Unit,
              viewModel: LocationViewModel = LocationViewModel(LocalContext.current)
) {

    Box{

        /** The actual map shows here */
        viewModel.getLocation()

        var longLat = viewModel.location


        GoogleMapView(LatLng(viewModel.location.latitude,
            viewModel.location.longitude))

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
            
            CustomButton(onClick = { viewModel.updateLocation(viewModel.location) }, text = "Update Location")

        }
    }
}

@Composable
fun GoogleMapView (
    location: LatLng
) {
//    val singapore = LatLng(1.35, 103.87)
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