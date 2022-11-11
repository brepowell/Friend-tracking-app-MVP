package com.css545.meetme.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.css545.meetme.R
import com.css545.meetme.ui.components.CustomButton


@Composable
fun MapScreen(onStopTrackButtonClicked: () -> Unit) {

    Box{
        val image1 = painterResource(R.drawable.google_maps_marker)
        Image(
            painter = image1,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomButton(
                onClick = { onStopTrackButtonClicked() },
                text = stringResource(id = R.string.screen_title_tracking_stop)
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun ShowMapPreview()
{
    MapScreen(onStopTrackButtonClicked = {})

}