package com.allcarsinone.allcarsinone

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.allcarsinone.allcarsinone.activities.InitialPageActivity
import com.allcarsinone.allcarsinone.activities.MainActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class AllCarsInOneFirebaseService : FirebaseMessagingService() {

    val TAG = "MAIN"

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        sendTokenToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From ${remoteMessage.from}")

        if(remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Notifications: ${remoteMessage.data}")
        }

        remoteMessage.notification?.let {
            Log.d(TAG, "Notification body: ${it.body}")
            sendNotification(it.body ?: "Body")
        }
    }

    private fun sendNotification(messageBody: String) {
        val intent = Intent(this, InitialPageActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Notification")
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setPriority(2)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("1",
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    private fun sendTokenToServer(token: String?) {
        val deviceToken = hashMapOf(
            "token" to token,
        )

        Firebase.firestore.collection("tokens").document("tokenid")
            .set(deviceToken)
    }

}