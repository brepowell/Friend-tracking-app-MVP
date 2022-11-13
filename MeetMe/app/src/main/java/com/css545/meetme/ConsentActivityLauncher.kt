package com.css545.meetme

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.css545.meetme.ui.theme.MeetMeTheme

class ConsentActivityLauncher : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        IntentFilter(Intent.ACTION_VIEW)
        setContent {
            MeetMeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MeetMeAppConsent()
                }
            }
        }
    }

    fun startMapIntent(){
        /** FROM THE CONSENT SCREEN, LAUNCH THE MAP WHEN CONSENT IS GIVEN */
        val navigate = Intent(
            this@ConsentActivityLauncher,
            MapsActivityLauncher::class.java)
        startActivity(navigate)
    }

}

