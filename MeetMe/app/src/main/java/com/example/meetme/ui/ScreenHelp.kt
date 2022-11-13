package com.css545.meetme.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.css545.meetme.R
import com.css545.meetme.ui.components.CustomButton

@Composable
fun HelpScreen(onSettingsButtonClicked: () -> Unit) {

    Column{
        Text(text = stringResource(id = R.string.help_header))
        Text(text = stringResource(id = R.string.help_info))
        CustomButton(onClick = { onSettingsButtonClicked() }, text = "Go To Settings")
    }
}


@Preview(showBackground = true)
@Composable
fun ViewHelpScreen()
{
    HelpScreen(onSettingsButtonClicked = {  })
}