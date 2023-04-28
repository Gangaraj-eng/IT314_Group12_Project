package com.mypackage.it314_health_center

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class ActivityHelp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.help_center)

        findViewById<ImageButton>(R.id.back_btn).setOnClickListener {
            finish()
        }
    }
}