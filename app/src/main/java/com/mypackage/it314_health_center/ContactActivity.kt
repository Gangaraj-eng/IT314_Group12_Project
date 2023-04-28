package com.mypackage.it314_health_center

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_us)

        findViewById<MaterialButton>(R.id.button3).setOnClickListener {
            finish()
            Toast.makeText(
                this,
                "Thanks for contacting, we will reach out to you soon",
                Toast.LENGTH_SHORT
            ).show()
        }

        findViewById<ImageButton>(R.id.back_btn).setOnClickListener {
            finish()
        }
    }
}