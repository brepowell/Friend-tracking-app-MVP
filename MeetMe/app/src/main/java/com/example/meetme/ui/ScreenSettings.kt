package com.css545.meetme.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.css545.meetme.data.SettingsState
import com.css545.meetme.ui.components.CustomButton
import com.css545.meetme.ui.components.CustomTextField
import com.css545.meetme.ui.components.ToggleSwitch

//@Preview
@Composable
fun SettingsScreen(
    settingsState: SettingsState,
    onUsernameChanged: (String) -> Unit,
    onLocationSharingChanged: (Boolean) -> Unit,
    onUpdatePasswordClicked: () -> Unit,
    onHelpButtonClicked: () -> Unit
) {

    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        // UserName
        CustomTextField(
            text = settingsState.username,
            onValueChange = {onUsernameChanged(it)},
            label = "Username"
        )

        Spacer(modifier = Modifier.height(10.dp))
        Divider(color = Color.Black, thickness = 1.dp)
        // Password
        Text("Change Password", style = MaterialTheme.typography.caption)
        var password by rememberSaveable { mutableStateOf("") }
        PasswordTextField(
            text = password,
            onValueChange = {password = it},
            label = "Current Password"
        )

        var newPassword by rememberSaveable { mutableStateOf("") }
        PasswordTextField(
            text = newPassword,
            onValueChange = {newPassword = it},
            label = "New Password"
        )

        var confirmPassword by rememberSaveable { mutableStateOf("") }
        PasswordTextField(
            text = confirmPassword,
            onValueChange = {confirmPassword = it},
            label = "Confirm Password"
        )

        CustomButton(
            onClick = onUpdatePasswordClicked,
            text = "Update"
        )

        // Select map icon

        // Toggle whether you can be found or not
        Spacer(modifier = Modifier.height(10.dp))
        Divider(color = Color.Black, thickness = 1.dp)
        Spacer(modifier = Modifier.height(10.dp))
        ToggleSwitch(
            label = "Allow Location sharing: ",
            checkedState = settingsState.isSharingLocation,
            onCheckedChange = onLocationSharingChanged
        )

        // P2 - Unit of distance


        // Help!
        CustomButton(onClick = onHelpButtonClicked, text = "Help!")

    }
}


@Composable
fun PasswordTextField(
    text: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = if(passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    
        leadingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                var icon = if(passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                Icon(
                    imageVector = icon, contentDescription = null
                )
            }
        }
    )
}


