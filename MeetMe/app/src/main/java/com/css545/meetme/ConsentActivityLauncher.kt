package com.css545.meetme

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.css545.meetme.ui.ConsentScreen
import com.css545.meetme.ui.theme.MeetMeTheme

class ConsentActivityLauncher : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetMeAppConsent()
        }
    }

    fun startMapIntent(){
        /** FROM THE CONSENT SCREEN, LAUNCH THE MAP WHEN CONSENT IS GIVEN */
        val navigate = Intent(
            this@ConsentActivityLauncher,
            MapsActivityLauncher::class.java)
    }

}

