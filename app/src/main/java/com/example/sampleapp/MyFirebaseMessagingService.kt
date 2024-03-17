package com.example.sampleapp

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.verloop.sdk.VerloopNotification

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // set a drawable to use as icon for notifications
        val icon = R.drawable.outline_10k_24

        // Notifications will be auto-ignored if it's not from Verloop. Default notification channel name will be "Verloop Chat Message"
        VerloopNotification.showNotification(
            this,
            icon,
            remoteMessage.getData(),
            "Your Notification Channel Name"
        )

        // Do anything else with your message.
    }
}