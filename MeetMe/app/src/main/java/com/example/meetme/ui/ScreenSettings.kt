package com.css545.meetme.ui

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button
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
import com.css545.meetme.ui.components.ToggleSwitch

//@Preview
@Composable
fun SettingsScreen(
    settingsState: SettingsState,
    onLocationSharingChanged: (Boolean) -> Unit,
    onUpdatePasswordClicked: () -> Unit,
    onHelpButtonClicked: () -> Unit
) {
    /** -------------- Email Input Validation ----------------------------- */
    var emailInput by rememberSaveable {
        mutableStateOf(settingsState.username)
    }

    val isErrorInEmail by remember {
        derivedStateOf {
            if (emailInput.isEmpty()) {
                false // The default value
            } else {
                // Returns true if email doesn't match the pattern
                Patterns.EMAIL_ADDRESS.matcher(emailInput).matches().not()
            }
        }
    }

    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        // UserName with filter
        EmailFilterTextField(
            isErrorInEmail = isErrorInEmail,
            value = emailInput,
            onValueChange = {
                emailInput = it
            }
        )

        /** -------------- Old Password Text Box ---------------------------------- */
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

        /** -------------- New Password Text Box ------------------------------------- */
        var newPassword by rememberSaveable { mutableStateOf("") }
        PasswordTextField(
            text = newPassword,
            onValueChange = {newPassword = it},
            label = "New Password"
        )

        /** -------------- Password Confirmation ------------------------------------- */
        var confirmPassword by rememberSaveable { mutableStateOf("") }
        val confirmationNotMatch by remember {
            derivedStateOf {
                if (confirmPassword.isEmpty()) {
                    false // default value
                } else {
                    // Returns true if passwords don't match
                    newPassword != confirmPassword
                }
            }
        }
        ConfirmPasswordTextField(
            confirmPasswordNotMatch = confirmationNotMatch,
            text = confirmPassword,
            onValueChange = {confirmPassword = it},
            label = "Confirm Password"
        )

        /** Clicking this button should update the password in the database */
        UpdatePasswordButton(
            onClick = {
                if (!confirmationNotMatch) {
                    onUpdatePasswordClicked()
                }
            },
            text = "Update"
        )

        // P2 - Select map icon

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

@OptIn(ExperimentalMaterial3Api::class)
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
                val icon = if(passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                Icon(
                    imageVector = icon, contentDescription = null
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailFilterTextField(
    isErrorInEmail: Boolean,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = "Username",
                modifier = Modifier.fillMaxWidth()
            )
        },
        placeholder = {
            Text(text = "Enter email address")
        },
        supportingText = {
            if(isErrorInEmail) {
                Text(text = "Enter a valid email address")
            }
        },
        isError = isErrorInEmail,
        singleLine = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmPasswordTextField(
    confirmPasswordNotMatch: Boolean,
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
                val icon = if(passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                Icon(
                    imageVector = icon, contentDescription = null
                )
            }
        },
        supportingText = {
            if (confirmPasswordNotMatch) {
                Text(text = "Password doesn't match")
            }
        },
        isError = confirmPasswordNotMatch,
        singleLine = true
    )
}

@Composable
fun UpdatePasswordButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp)
    ) {
        Text(text)
    }
}
