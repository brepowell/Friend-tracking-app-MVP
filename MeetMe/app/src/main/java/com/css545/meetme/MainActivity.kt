package com.css545.meetme

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.css545.meetme.ui.theme.MeetMeTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /** ALLOW THE APP TO BE OPENED BY ANOTHER APP */
        IntentFilter(Intent.ACTION_VIEW)

        setContent {
            MeetMeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MeetMeAppStart()
                }
            }
        }
    }

    fun consentIntent(){
        /** LEAVE THE WAITING SCREEN AND GO TO THE CONSENT SCREEN */
        // TODO: THIS IS A HACK. WE NEED TO MAKE SOME SORT OF LISTENER THAT WILL CALL THIS
        //  RATHER THAN HAVING A BUTTON DO THIS STEP.
        val navigate = Intent(
            this@MainActivity,
            ConsentActivityLauncher::class.java)
    }
}