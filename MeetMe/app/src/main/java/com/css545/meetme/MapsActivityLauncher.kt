package com.css545.meetme

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MapsActivityLauncher : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MeetMeAppMap()
        }
    }

    fun stopMapIntent(){
        /** LEAVE THE MAP -- END THE SESSION -- RETURN TO START */
        val navigate = Intent(
            this@MapsActivityLauncher,
            MainActivity::class.java)
    }
}