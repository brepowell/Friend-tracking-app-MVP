package com.css545.meetme.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.css545.meetme.R
import com.css545.meetme.ui.components.CustomButton

@Composable
fun StopTrackingScreen(
    onYesClicked: () -> Unit,
    onNoClicked: () -> Unit
) {
    Column{
        Text(
            text = stringResource(id = R.string.screen_title_tracking_stop),
            fontSize = 36.sp,
            modifier = Modifier.align(Alignment.Start)
            )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.tracking_end_prompt),
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start)
            )

        Spacer(modifier = Modifier.height(8.dp))

        CustomButton(onClick = onYesClicked, text = "Yes")
        CustomButton(onClick = onNoClicked, text = "No")
    }
}





@Preview(showBackground = true)
@Composable
fun StopTrackingScreenPreview()
{
    StopTrackingScreen(onNoClicked = {}, onYesClicked = {})
}