package com.mypackage.it314_health_center.startups

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mypackage.it314_health_center.Doctor_Homepage
import com.mypackage.it314_health_center.PatientHomePage
import com.mypackage.it314_health_center.helpers.dbPaths

class StartScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        val currMode = getSharedPreferences(dbPaths.SharedPreference, MODE_PRIVATE).getInt(
            dbPaths.Current_Mode,
            AppCompatDelegate.getDefaultNightMode()
        )
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        // StartScreen will be displayed here

        // Display start screen for 2 seconds and then redirect to HomeScreen or Login page
        Handler(Looper.getMainLooper()).postDelayed({

            // get the Firebase Authentication instance
            val mAuth = FirebaseAuth.getInstance();

            // get the Firebase Database reference instance
            val mdbRef = FirebaseDatabase.getInstance().reference


            // check if user is logged in
            if (mAuth.currentUser != null) {
                val userId = mAuth.currentUser!!.uid
                // check if user is doctor
                mdbRef.child(dbPaths.DoctorIds).child(userId).get()
                    .addOnSuccessListener {
                        if (it.exists()) {
                            // if user exists in doctors list, then redirect to doctor home-screen
                            val intent = Intent(this, Doctor_Homepage::class.java)
                            startActivity(intent)

                            finish() // close the current activity
                            if (currMode == AppCompatDelegate.MODE_NIGHT_YES) {
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                            } else {
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                            }
                        } else {

                            // the user is patient
                            // check if profile details are filled
                            mdbRef.child(dbPaths.USERS).child(userId).child(dbPaths.USER_DETAILS)
                                .get().addOnSuccessListener { it1 ->
                                    if (it1.exists()) {
                                        // profile is completed
                                        // redirect to homePage
                                        val intent = Intent(this, PatientHomePage::class.java)
                                        startActivity(intent)

                                        finish() // close the current activity
                                        if (currMode == AppCompatDelegate.MODE_NIGHT_YES) {
                                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                                        } else {
                                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                                        }
                                    } else {

                                        // profile details not exists
                                        // redirect to details page
                                        val intent = Intent(this, UserDetailsActivity::class.java)
                                        startActivity(intent)

                                        finish() // close the current activity
                                        if (currMode == AppCompatDelegate.MODE_NIGHT_YES) {
                                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                                        } else {
                                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                                        }
                                    }
                                }
                        }
                    }
            } else {

                // if not logged in, then redirect to Login page
                val intent = Intent(this, Login::class.java)
                startActivity(intent)

                finish() // close the current activity
                if (currMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }

            }
        }, 2000) // 2000 ms=2 seconds

    }

    override fun recreate() {
        finish()
    }
}