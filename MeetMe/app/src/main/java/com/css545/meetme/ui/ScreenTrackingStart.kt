package com.css545.meetme.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.css545.meetme.R
import com.css545.meetme.data.SettingsState
import com.css545.meetme.ui.components.CustomButton
import java.text.NumberFormat

//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.layout.ContentScale
//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter

@Composable
fun StartTrackingScreen(
    settingsState: SettingsState,
    onStartTrackingButtonClicked: (String) -> Unit,
) {
    var amountInput by rememberSaveable { mutableStateOf(settingsState.trackLength.toString()) }
    var phoneNumber by rememberSaveable { mutableStateOf(settingsState.trackLength.toString()) }

    //toDoubleOrNull converts an int to a double
    val trackingDuration = amountInput.toDoubleOrNull() ?: 0.0
    val bill = billCalculator(trackingDuration)
    //val timeExpiration = "5:00 PM PST"

    //IMAGES
    //val image1 = painterResource(R.drawable.person_silhouette_1)
    //val image2 = painterResource(R.drawable.person_silhouette_2)
    //val image3 = painterResource(R.drawable.person_silhouette_3)

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
        Spacer(modifier = Modifier.height(8.dp))

        /** --------------- Enter the phone number ------------------ */
        //tracking_start_friends
        Text(
            text = stringResource(id = R.string.tracking_start_friends),
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        PhoneNumberEntryField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
            }
        )

        /** Friend(s) -- placeholder images for prototype */
/*
        Row (modifier = Modifier.padding(8.dp)) {
            Image(
                painter = image1,
                contentDescription = null,
                modifier = Modifier
                    .height(42.dp)
                    .width(42.dp)
            )
            Image(
                painter = image2,
                contentDescription = null,
                modifier = Modifier
                    .height(42.dp)
                    .width(42.dp)
            )
            Image(
                painter = image3,
                contentDescription = null,
                modifier = Modifier
                    .height(42.dp)
                    .width(42.dp)
            )
        }
*/
        Spacer(modifier = Modifier.height(8.dp))

        /**------------- DURATION: Header above text field -------------- */
        Text(
            text = stringResource(
                id = R.string.tracking_start_duration),
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        /**------------- DURATION: TextField to enter the hours -------------- */
        TimeLimitEntryField(
            value = amountInput,
            onValueChange = {
                amountInput = it

            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        /**----------------------Time Expiration-------------- */
        //<string name="tracking_start_tracking_expiration">Tracking expires: %s </string>
        /*
        Text(
            text = stringResource(
                id = com.css545.meetme.R.string.tracking_start_tracking_expiration,
                timeExpiration),
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
         */

        /**-----------------------Bill amount ----------------- */
        /*
        Text(
            text = stringResource(id = R.string.tracking_start_bill_amount, bill),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(48.dp))
        */

        /**-----------------SEND INVITATION BUTTON --------------------------*/
        InviteToStartTrackingButton(onClick = { onStartTrackingButtonClicked(amountInput) })
    }
}

@Composable
fun PhoneNumberEntryField(value: String, onValueChange: (String) -> Unit){
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = stringResource(id = R.string.tracking_start_phone_number),
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

@Composable
fun InviteToStartTrackingButton(
    onClick: () -> Unit,
) {
    CustomButton(
        onClick = { onClick() },
        text = stringResource(id = R.string.tracking_start_button)
    )
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
    StartTrackingScreen(
        settingsState = SettingsState(),
        onStartTrackingButtonClicked = {})
}