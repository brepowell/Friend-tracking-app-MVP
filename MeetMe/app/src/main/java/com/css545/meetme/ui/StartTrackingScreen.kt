package com.css545.meetme.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/*
import androidx.compose.material.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import java.text.NumberFormat
 */

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

    /*
    var amountInput by remember { mutableStateOf("") }
    //toDoubleOrNull converts an int to a double
    val time = amountInput.toDoubleOrNull() ?: 0.0
    val bill = billCalculator(time)
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)){
        Text(
            text = stringResource(id = R.string.calculate_tip),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        //Spacers add space below different fields
        Spacer(modifier = Modifier.height(16.dp))
        TimeLimitEntryField(amountInput, onValueChange = {amountInput = it})
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.tip_amount, bill),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

     */
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

/*
@Composable
fun TimeLimitEntryField(value: String, onValueChange: (String) -> Unit){
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = stringResource(id = R.string.tracking_start_units_hours),
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

 */

/*
private fun billCalculator(time: Double, costPerHour: Double = 15.0): String{
    val bill = costPerHour / 100 * time
    return NumberFormat.getCurrencyInstance().format(bill)
    //Formats the total bill as a dollar amount for printing
}
*/

//+++++++++++++++++++++PREVIEW +++++++++++++++++
@Preview(showBackground = true)
@Composable
fun PreviewStartTracking() {
    StartTrackingScreen(onConsentButtonClicked = {},
        onStartTrackingButtonClicked = {})
}