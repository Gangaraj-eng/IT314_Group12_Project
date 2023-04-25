package com.mypackage.it314_health_center

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.button.MaterialButton

class ConfirmOrder : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var MaterialButton: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_order)

        window.statusBarColor = Color.WHITE
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
            true

        MaterialButton = findViewById(R.id.confirmOrder)
        progressBar = findViewById(R.id.loadingView)

        MaterialButton.setOnClickListener {
            // validate details
            MaterialButton.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed({
                progressBar.visibility = View.GONE
                findViewById<TextView>(R.id.order_placed_message).visibility = View.VISIBLE
            }, 1000)
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, PatientHomePage::class.java))
                finish()
            }, 3000)
        }
        findViewById<ImageButton>(R.id.back_btn).setOnClickListener {
            finish()
        }
    }
}