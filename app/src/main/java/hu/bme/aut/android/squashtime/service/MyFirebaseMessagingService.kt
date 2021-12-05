package hu.bme.aut.android.squashtime.service

import android.util.Log
import com.google.firebase.messaging.RemoteMessage

import com.google.firebase.messaging.FirebaseMessagingService


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.from)
        Log.d(
            TAG, "Notification Message Body: " + remoteMessage.notification!!
                .body
        )
    }

    companion object {
        private const val TAG = "FCM Service"
    }
}