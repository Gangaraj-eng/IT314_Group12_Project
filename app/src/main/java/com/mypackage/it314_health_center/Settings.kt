package com.mypackage.it314_health_center

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.mypackage.it314_health_center.helpers.dbPaths


class Settings: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val notification_enable: Switch = findViewById(R.id.notification_enable)

        if (notification_enable.isChecked) {
            val mPrefs = getSharedPreferences("settings", 0)
            val prefsEditor: SharedPreferences.Editor = mPrefs.edit();
            prefsEditor.putString(dbPaths.NOTIFICATION_ENABLED, "1");
            prefsEditor.apply()
        } else {
            val mPrefs = getSharedPreferences("Settings", 0)
            val prefsEditor: SharedPreferences.Editor = mPrefs.edit();
            prefsEditor.putString(dbPaths.NOTIFICATION_ENABLED, "0");
            prefsEditor.apply()
        }


    }
}
