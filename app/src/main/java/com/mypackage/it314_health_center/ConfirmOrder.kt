package com.mypackage.it314_health_center

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import org.w3c.dom.Text

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
        val errorView=findViewById<TextView>(R.id.error_address)
        MaterialButton.setOnClickListener {
            val validate=validateDeliveryAddress(findViewById<TextInputEditText>(R.id.city_field).text.toString(),
                findViewById<TextInputEditText>(R.id.statefield).text.toString(),
                findViewById<TextInputEditText>(R.id.address_field).text.toString(),
                findViewById<TextInputEditText>(R.id.pincode).text.toString())

            if(!validate.isEmpty())
            {
                errorView.visibility=View.VISIBLE
                errorView.text=validate
                Handler(Looper.getMainLooper()).postDelayed({
                                                            errorView.visibility=View.GONE
                },2000)
                return@setOnClickListener
            }

            (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(it.windowToken, 0)

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

    companion object {

        fun validateDeliveryAddress(
            city: String,
            state: String,
            addres: String,
            pincode: String
        ): String {
            if (city.trim().isEmpty()) return "Invalid city name"
            if (state.trim().isEmpty()) return "Invalid state name"
            if (addres.trim().length < 20) return "Address should be atleast 20 characters"
            if (pincode.trim().length != 6) return "pincode should be 6 digits"
            return ""
        }
    }

}