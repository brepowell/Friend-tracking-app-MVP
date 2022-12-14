package com.css545.meetme.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.css545.meetme.R
import com.css545.meetme.data.SettingsState
import com.css545.meetme.ui.components.CustomButton

@Composable
fun ConsentScreen(
    settingsState: SettingsState,
    onYesClicked: () -> Unit,
    onNoClicked: () -> Unit,
    sessionID: Int = 0,
) {
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        //Prompt at the top
        Text(
            text = stringResource(id = R.string.tracking_handshake_consent_page_prompt,
                settingsState.trackLength.toString()),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        Text(text= "session ID: $sessionID")

        //Spacers add space below different fields
        Spacer(modifier = Modifier.height(8.dp))

        //Ask for consent
        Text(
            text = stringResource(
                id = R.string.tracking_handshake_consent_ask,
                settingsState.trackLength.toString()
            ),
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        //YES BUTTON
        CustomButton(
            onClick = onYesClicked,
            text = stringResource(
                id = R.string.button_yes))

        //NO BUTTON
        CustomButton(
            onClick = onNoClicked,
            text = stringResource(
                id = R.string.button_no))
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewConsentScreen() {
    ConsentScreen(SettingsState(),{}, {})
}