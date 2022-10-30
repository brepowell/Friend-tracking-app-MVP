package com.css545.meetme.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter

@Composable
fun StartTrackingScreen(onStartTrackingButtonClicked: () -> Unit,
                        onConsentButtonClicked: () -> Unit
                        )
{
    var amountInput by remember { mutableStateOf("") }
    //toDoubleOrNull converts an int to a double
    val trackingDuration = amountInput.toDoubleOrNull() ?: 0.0
    val bill = billCalculator(trackingDuration)
    val timeExpiration = "5:00 PM PST"

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)){
        //Prompt at the top
        Text(
            text = stringResource(id = com.css545.meetme.R.string.tracking_start_prompt),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        //Spacers add space below different fields
        Spacer(modifier = Modifier.height(8.dp))

        //Friend(s)
        //tracking_start_friends
        Text(
            text = stringResource(id = com.css545.meetme.R.string.tracking_start_friends),
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        //Tracking Duration - header above text field
        Text(
            text = stringResource(
                id = com.css545.meetme.R.string.tracking_start_duration),
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        //TextField to enter the hours
        TimeLimitEntryField(amountInput, onValueChange = {amountInput = it})
        Spacer(modifier = Modifier.height(24.dp))

        //Time Expiration
        //<string name="tracking_start_tracking_expiration">Tracking expires: %s </string>
        Text(
            text = stringResource(
                id = com.css545.meetme.R.string.tracking_start_tracking_expiration,
                timeExpiration),
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        //Bill amount
        Text(
            text = stringResource(id = com.css545.meetme.R.string.tracking_start_bill_amount, bill),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(48.dp))

        //Buttons
        StartTrackingButton(onClick = onStartTrackingButtonClicked)
        ConsentButton(onClick = onConsentButtonClicked)
    }
}

@Composable
fun StartTrackingButton(
    onClick: () -> Unit,
    //modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Start Tracking")
    }
}

@Composable
fun ConsentButton (
    onClick: () -> Unit,
    //modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Consent")
    }
}

@Composable
fun TimeLimitEntryField(value: String, onValueChange: (String) -> Unit){
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = stringResource(id = com.css545.meetme.R.string.tracking_start_units_hours),
                modifier = Modifier.fillMaxWidth()
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true
    )
    //label - placeholder text for an empty text field
    //KeyboardType.Number - uses only the numbers keyboard - no other options
    //singleLine - ensures the input is on one line
}

private fun billCalculator(time: Double, costPerHour: Double = 15.0): String{
    val bill = costPerHour / 100 * time
    return NumberFormat.getCurrencyInstance().format(bill)
    //Formats the total bill as a dollar amount for printing
}

/*
private fun ExpirationTime(): String{
    val currentTime = LocalDateTime.now()
    val formatForTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
    return currentTime.format(formatForTime)
}
 */

//+++++++++++++++++++++PREVIEW +++++++++++++++++
@Preview(showBackground = true)
@Composable
fun PreviewStartTracking() {
    StartTrackingScreen(onConsentButtonClicked = {},
        onStartTrackingButtonClicked = {})
}