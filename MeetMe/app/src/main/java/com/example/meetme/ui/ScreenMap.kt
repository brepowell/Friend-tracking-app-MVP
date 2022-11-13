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


@Composable
fun MapScreen(onStopTrackButtonClicked: () -> Unit,
              viewModel: LocationViewModel = viewModel()
) {

    Box{

        /** The actual map shows here */
        GoogleMapView(LatLng(47.8209, -122.3151))

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
    location: LatLng = LatLng(1.35, 103.87)
) {
//    val singapore = LatLng(1.35, 103.87)
    val singState = MarkerState(position = location)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = singState,
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