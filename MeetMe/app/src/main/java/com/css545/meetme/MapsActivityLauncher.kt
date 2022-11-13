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

class MapsActivityLauncher : ComponentActivity() {

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
                    MeetMeAppMap()
                }
            }
        }

    }

    fun stopMapIntent(){
        /** LEAVE THE MAP -- END THE SESSION -- RETURN TO START */
        val navigate = Intent(
            this@MapsActivityLauncher,
            MainActivity::class.java)
    }
}