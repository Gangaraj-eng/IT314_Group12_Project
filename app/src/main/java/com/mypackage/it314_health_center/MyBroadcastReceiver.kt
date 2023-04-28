package com.mypackage.it314_health_center

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat


class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {


//        val notificationIntent = Intent(context, BookAppointment::class.java)
//        notificationIntent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP  or Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val notificationIntent = Intent(context, BookAppointment::class.java)

        notificationIntent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val contentIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            0,
//            notificationIntent,
//            PendingIntent.FLAG_IMMUTABLE
//        )

//        builder.setLatestEventInfo(context, title, message, intent)
//        builder.flags = builder.flags or Notification.FLAG_AUTO_CANCEL

        val builder = NotificationCompat.Builder(context, "my_channel_id")
            .setSmallIcon(R.drawable.ic_app_icon)
            .setContentTitle("Appointment Reminder")
            .setContentText("Your appointment starts now.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(contentIntent)


//        val
//        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//        val notification = intent.getParcelableExtra<Notification>(NOTIFICATION)
//        val id = intent.getIntExtra(NOTIFICATION_ID, 0)

//        val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding

            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        notificationManager.notify(0, builder.build())
    }
}