package com.css545.meetme.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.material.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StartTrackingScreen(onStartTrackingButtonClicked: () -> Unit,
                        onConsentButtonClicked: () -> Unit
                        )
{
    Column {
        Text("Start Tracking screen")
        StartTrackingButton(onClick = onStartTrackingButtonClicked)
        ConsentButton(onClick = onConsentButtonClicked)
    }
}

@Composable
fun StartTrackingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp)
    ) {
        Text("Start Tracking")
    }
}

@Composable
fun ConsentButton (
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp)
    ) {
       Text("Consent")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartTracking() {
    StartTrackingScreen(onConsentButtonClicked = {},
        onStartTrackingButtonClicked = {})
}
