package com.mypackage.it314_health_center

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat

class Notification: AppCompatActivity() {

    private lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification)

        button = findViewById(R.id.button)

        // Create a NotificationChannel
        createNotificationChannel()

        val mPrefs = getSharedPreferences("IDvalue", MODE_PRIVATE)
        val str = mPrefs.getString("save", "")

        if(str=="hello") {
            Toast.makeText(this, "hello", Toast.LENGTH_LONG).show()
        }

            button.setOnClickListener {
                // Schedule the Notification
                val notificationIntent = Intent(this, MyBroadcastReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    this,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )

                val futureInMillis = System.currentTimeMillis() + 10000

                val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent)

                Toast.makeText(this, "Notification Scheduled", Toast.LENGTH_LONG).show()

            }



    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My channel"
            val descriptionText = "My channel description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("my_channel_id", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
