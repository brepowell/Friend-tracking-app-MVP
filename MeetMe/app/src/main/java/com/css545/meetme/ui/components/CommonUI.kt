package com.css545.meetme.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .widthIn(min = 250.dp)
    ) {
        Text(text)
    }
}


@Composable
fun CustomTextField(
    text: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text(label) }
    )
}

@Composable
fun ToggleSwitch(
    label: String,
    checkedState: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(
            border = BorderStroke(2.dp, Color.LightGray),
            shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Text(label)
        Switch(
            checked = checkedState,
            onCheckedChange = onCheckedChange
        )
    }
}


@Composable
fun CustomAlert(
    text: String
){

}