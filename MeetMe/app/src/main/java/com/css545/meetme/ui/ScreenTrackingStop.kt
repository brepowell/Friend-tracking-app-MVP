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

@Composable
fun StopTrackingScreen() {
    Column{
        Text(
            text = stringResource(id = R.string.tracking_stop),
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

        YesStopButton {}
        NoDoNotStopButton {}
    }
}


@Composable
fun YesStopButton(
    onClick: () -> Unit,
    //modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.agree),
        )
    }
}

@Composable
fun NoDoNotStopButton(
    onClick: () -> Unit,
    //modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.disagree),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StopTrackingScreenPreview()
{
    StopTrackingScreen()
}