package com.mypackage.it314_health_center

import android.R
import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.mypackage.it314_health_center.startups.Login


class SideBar: AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    private lateinit var mNavigationView: NavigationView
    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDrawerLayout = findViewById(R.id.activity_main_drawer)
        mNavigationView = findViewById(R.id.nav_view)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                val intent = Intent(this, PatientHomePage::class.java)
                startActivity(intent)
            }

            R.id.nav_profile -> {
                val intent = Intent(this, PatientProfile::class.java)
                startActivity(intent)
            }

            R.id.nav_history -> {
                val intent = Intent(this, PatientHistory::class.java)
                startActivity(intent)
            }

            R.id.nav_settings -> {
                val intent = Intent(this, Settings::class.java)
                startActivity(intent)
            }

            R.id.nav_logout -> {

                FirebaseAuth.getInstance().signOut();

                val intent = Intent(this, Login::class.java)
                startActivity(intent)

            }
        }

        //close navigation drawer
        mDrawerLayout.closeDrawer(GravityCompat.START)
        return true

    }

}