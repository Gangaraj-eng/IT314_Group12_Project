package com.mypackage.it314_health_center.startups

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mypackage.it314_health_center.MainActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // launch main activity after 2 seconds
        Handler().postDelayed({
            val mAuth = FirebaseAuth.getInstance();
            if (mAuth!!.currentUser != null) {
                mAuth.currentUser?.let {
                    FirebaseDatabase.getInstance().reference
                        .child("users").child(it.uid)
                        .child("relative_details").get()
                        .addOnSuccessListener {
                            if (it.exists()) {
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            } else {
                                startActivity(Intent(this, user_details_activity::class.java))
                                finish()
                            }
                        }
                }

            } else {

                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000)

    }
}