package com.css545.meetme

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity

/*
class Handshake : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        TODO("Not yet implemented")
    }

    fun composeMmsMessage(message: String, attachment: Uri) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            data = Uri.parse("smsto:")  // This ensures only SMS apps respond
            putExtra("sms_body", message)
            putExtra(Intent.EXTRA_STREAM, attachment)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}*/