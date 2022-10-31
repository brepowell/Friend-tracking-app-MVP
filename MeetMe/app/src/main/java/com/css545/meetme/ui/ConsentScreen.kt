package com.css545.meetme.ui

import androidx.compose.foundation.layout.*
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
fun ConsentScreen() {
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        //Prompt at the top
        Text(
            text = stringResource(id = R.string.tracking_handshake_notification, "Breanna"),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        //Spacers add space below different fields
        Spacer(modifier = Modifier.height(8.dp))

        //Friend(s)
        //tracking_start_friends
        Text(
            text = stringResource(
                id = R.string.tracking_handshake_consent, "7"),
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start)
        )
        YesButton {}
        NoButton {}
    }
}

@Composable
fun YesButton(
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
fun NoButton(
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
fun PreviewConsentScreen() {
    ConsentScreen()
}