package com.mypackage.it314_health_center.startups

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mypackage.it314_health_center.Doctor_Homepage
import com.mypackage.it314_health_center.MainActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
//        val names= arrayOf("Dr. Rajesh Singh","Dr. Anjali Sharma","Dr.Rohit Gupta")
//        val mails= arrayOf("doctor1@gmail.com","doctor2@gmail.com","doctor3@gmail.com")
//        val types= arrayOf("General","Psychologist","Dentist")
//        val i=0
//            FirebaseAuth.getInstance().createUserWithEmail    AndPassword(mails[i],"admin")
//                .addOnSuccessListener {
//                    val uid = FirebaseAuth.getInstance().currentUser!!.uid
//                    val doctor = Doctor(uid.toString(), names[i], mails[i], types[i])
//                    FirebaseDatabase.getInstance().reference.child("doctors")
//                        .child(uid).setValue(doctor).addOnSuccessListener {
//                            Log.d("123","created ")
//                        }
//                }

        // launch main activity after 2 seconds
        Handler().postDelayed({
            val mAuth = FirebaseAuth.getInstance();
            if (mAuth!!.currentUser != null) {
                mAuth.currentUser?.let {
                    FirebaseDatabase.getInstance().reference.child("doctors")
                        .child(it.uid).get()
                        .addOnSuccessListener {
                            if(it.exists())
                            {
                                startActivity(Intent(this@SplashScreen,Doctor_Homepage::class.java))
                                finish()
                            }
                            else{
                    FirebaseDatabase.getInstance().reference
                        .child("users").child(mAuth.currentUser!!.uid)
                        .child("user_details").get()
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