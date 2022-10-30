package com.css545.meetme.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MapScreen(onSettingsButtonClicked: () -> Unit,
              onTrackButtonClicked: () -> Unit) {
    Column {
        Text("Map Screen")
        SettingsButton(onClick = { onSettingsButtonClicked() })
        TrackButton(onClick = { onTrackButtonClicked() })
    }
}

@Composable
fun SettingsButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp)
    ) {
        Text("Go To Settings")
    }
}

@Composable
fun TrackButton(
onClick: () -> Unit,
modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp)
    ) {
        Text("Track")
    }
}