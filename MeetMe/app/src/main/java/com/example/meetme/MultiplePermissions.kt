package com.css545.meetme
/*
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.css545.meetme.ui.components.CustomButton

/** INSTRUCTIONS: https://www.geeksforgeeks.org/android-run-time-permissions-using-jetpack-compose/ */

@Composable
fun runtimePermissions() {
    val activity = (LocalContext.current as? Activity)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.permissions_runtime_permissions),
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(36.dp))

        CustomButton(
            onClick = {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 101, activity!!)
            },
            text = stringResource(id = R.string.button_request_permissions)
        )

    }
}

// on below line we are creating a check permission method to check the permissions.
fun checkPermission(permission: String, requestCode: Int, activity: Activity) {
    // on below line we are checking if the permission is denied.
    if (ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_DENIED
    ) {
        // if the permission is denied we are calling
        // request permission method to request permissions.
        ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
    } else {
        // this method will be called if the permissions are already granted.
        // On below line we are displaying a toast message if permissions are granted.
        Toast.makeText(activity, "Permission already granted..", Toast.LENGTH_SHORT).show()
    }
}
*/