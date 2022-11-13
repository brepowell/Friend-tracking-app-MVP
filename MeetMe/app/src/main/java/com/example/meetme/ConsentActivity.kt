package com.css545.meetme

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.css545.meetme.ui.components.CustomButton

class ConsentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            ConsentGreeting(name = "Jardi")

            CustomButton(
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    intent.putExtra("screen", MeetMeScreen.Settings.name)
                    context.startActivity(intent, null)
                },
                text = "Go to Main Activity"
            )
        }

    }
}

@Composable
fun ConsentGreeting(name: String) {
    Text(text = "Hello $name!")
}