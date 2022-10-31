package com.css545.meetme.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.css545.meetme.R

@Composable
fun StopTrackingScreen() {
    Column{
        Text(
            text = stringResource(id = R.string.stop_tracking_screen),
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start)
            )
        Text(
            text = stringResource(id = R.string.tracking_end_prompt),
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start)
            )
    }
}

@Preview(showBackground = true)
@Composable
fun StopTrackingScreenPreview()
{
    StopTrackingScreen()
}