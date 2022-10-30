package com.css545.meetme.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.css545.meetme.ui.components.CustomButton

@Composable
fun MapScreen(onSettingsButtonClicked: () -> Unit,
              onTrackButtonClicked: () -> Unit) {
    Column {
        Text("Map Screen")
        CustomButton(onClick = { onSettingsButtonClicked() }, text = "Go To Settings")
        CustomButton(onClick = { onTrackButtonClicked() }, text = "Track")
    }
}