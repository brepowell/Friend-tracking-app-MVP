package com.css545.meetme.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.css545.meetme.R
import com.css545.meetme.data.SettingsState
import com.css545.meetme.ui.components.CustomButton

@Composable
fun StartTrackingScreen(
    settingsState: SettingsState,
    onStartTrackingButtonClicked: (String) -> Unit,
    onMapButtonClicked: () -> Unit // TODO: REMOVE LATER
) {
    var amountInput by rememberSaveable {
        mutableStateOf(settingsState.trackLength.toString())
    }

    /** ----------------- Input Validation -------------------- */
    val isErrorInDuration by remember {
        derivedStateOf {
            if (amountInput.isEmpty()){
                false //0 is the default value
            }else{
                //true if amountInput is out of bounds
                //false if between 1 and 168 hours
                amountInput.toInt() < 1 || amountInput.toInt() > 168
            }
        }
    }

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)){

        /** --------------- Prompt at the top ------------------ */
        Text(
            text = stringResource(id = R.string.tracking_start_prompt),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        //Spacers add space below different fields
        Spacer(modifier = Modifier.height(16.dp))

        /**------------- DURATION: Header above text field -------------- */
        Text(
            text = stringResource(
                id = R.string.tracking_start_duration),
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        /**------------- DURATION: TextField to enter the hours -------------- */
        TimeLimitEntryField(
            isErrorInDuration = isErrorInDuration,
            duration = amountInput,
            onValueChange = {
                amountInput = it

            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        /**------------- NOTICE: THIS WILL OPEN A TEXT MESSAGE -------------- */
        Text(
            text = stringResource(
                id = R.string.tracking_start_warning_about_opening_SMS),
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        /**-----------------SEND INVITATION BUTTON --------------------------*/

        InviteToStartTrackingButton(onClick = { onStartTrackingButtonClicked(amountInput) })

        // TODO: REMOVE LATER
        CustomButton(onClick = { onMapButtonClicked() }, text = "GO TO MAP -- FOR TESTING")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeLimitEntryField(isErrorInDuration: Boolean,
                        duration: String, onValueChange: (String) -> Unit){

    TextField(
        value = duration,
        onValueChange = onValueChange,
        label = {
            Text(
                text = stringResource(id = R.string.tracking_start_units_hours),
                modifier = Modifier.fillMaxWidth(),
            )
        },
        placeholder = {
            Text(text = "number of hours")
        },
        supportingText = {
            if(isErrorInDuration) {
                Text(text = "Enter a number between 1 and 168")
            }
        },
        isError = isErrorInDuration,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true
    )
    //label - placeholder text for an empty text field
    //KeyboardType.Number - uses only the numbers keyboard - no other options
    //singleLine - ensures the input is on one line
}

@Composable
fun InviteToStartTrackingButton(
    onClick: () -> Unit,
) {
    CustomButton(
        onClick = { onClick() },
        text = stringResource(id = R.string.tracking_start_button)
    )
}

//+++++++++++++++++++++PREVIEW +++++++++++++++++
@Preview(showBackground = true)
@Composable
fun PreviewStartTracking() {
    StartTrackingScreen(
        settingsState = SettingsState(),
        onStartTrackingButtonClicked = {},
        onMapButtonClicked = {} // TODO: REMOVE LATER
        )
}