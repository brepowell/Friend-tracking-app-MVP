package com.css545.meetme.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.css545.meetme.R
import com.css545.meetme.ui.components.CustomButton
import com.css545.meetme.ui.theme.colorRichBlackFogra39
import com.css545.meetme.ui.theme.colorTiffanyBlue

@Composable
fun WaitingForConsentScreen(
    onCancelButtonClicked: () -> Unit,
    // TODO: THE CONTINUE BUTTON IS TEMPORARY. IT NEEDS TO BE REMOVED FROM THE REAL DEAL
    onContinueButtonClicked: () -> Unit
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isSystemInDarkTheme())
                    colorRichBlackFogra39
                else colorTiffanyBlue
            )
            .padding(16.dp)
    )
    {
        Text(
            text = stringResource(id = R.string.waiting_for_consent),
            color = Color.White,
            fontSize = 24.sp
        )

        //Spacers add space below different fields
        Spacer(modifier = Modifier.height(8.dp))

        /***/
        Icon(
            imageVector = Icons.Default.Email,
            contentDescription = "Envelope Icon",
            modifier = Modifier.size(150.dp),
            tint = Color.White
        )

        //Spacers add space below different fields
        Spacer(modifier = Modifier.height(24.dp))

        /** CANCEL BUTTON */
        CustomButton(
            onClick = { onCancelButtonClicked() },
            text = stringResource(id = R.string.button_cancel)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // TODO: THIS BUTTON IS TEMPORARY. IT NEEDS TO BE REMOVED FROM THE REAL DEAL
        CustomButton(
            onClick = { onContinueButtonClicked() },
            text = stringResource(id = R.string.button_continue)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewWaitingForConsentScreen()
{
    // TODO: THE CONTINUE BUTTON IS TEMPORARY. IT NEEDS TO BE REMOVED FROM THE REAL DEAL
    WaitingForConsentScreen(onCancelButtonClicked = {}, onContinueButtonClicked = {})
}