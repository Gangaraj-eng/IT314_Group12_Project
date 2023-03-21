package com.mypackage.it314_health_center

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.mypackage.it314_health_center.startups.Login

class EmailVerificationAfterSignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_verification_after_sign_up)
        findViewById<MaterialButton>(R.id.ok_btn).setOnClickListener {
            startActivity(Intent(this,Login::class.java))
            finish()
        }
    }
}