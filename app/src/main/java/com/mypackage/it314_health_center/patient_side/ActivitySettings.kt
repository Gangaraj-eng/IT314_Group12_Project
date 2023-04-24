package com.mypackage.it314_health_center.patient_side

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.helpers.dbPaths

class ActivitySettings : AppCompatActivity() {
    private lateinit var mode_change:SwitchCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val currMode=getSharedPreferences(dbPaths.SharedPreference, MODE_PRIVATE).getInt(dbPaths.Current_Mode,AppCompatDelegate.getDefaultNightMode())

        mode_change = findViewById(R.id.mode_switch)

        findViewById<ImageButton>(R.id.back_btn).setOnClickListener {
            finish()
        }


        if(currMode==AppCompatDelegate.MODE_NIGHT_YES)
        {
            mode_change.isChecked=true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else{
            mode_change.isChecked=false
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        mode_change.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                getSharedPreferences(dbPaths.SharedPreference, MODE_PRIVATE).edit()
                    .putInt(dbPaths.Current_Mode,AppCompatDelegate.MODE_NIGHT_YES).apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                getSharedPreferences(dbPaths.SharedPreference, MODE_PRIVATE).edit()
                    .putInt(dbPaths.Current_Mode,AppCompatDelegate.MODE_NIGHT_NO).apply()
            }
        }

        val notification_enable: SwitchCompat = findViewById(R.id.notifications_switch)

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

    override fun recreate() {
        finish()
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)

        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
    }
}