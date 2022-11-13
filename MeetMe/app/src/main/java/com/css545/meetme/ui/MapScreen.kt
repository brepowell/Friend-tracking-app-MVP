package com.css545.meetme.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.css545.meetme.R
import com.css545.meetme.ui.components.CustomButton
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker


@Composable
fun MapScreen(onTrackButtonClicked: () -> Unit) {

    val singapore = LatLng(1.35, 103.87)
    val singState = MarkerState(position = singapore)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
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
    MapScreen(onTrackButtonClicked = {})

}