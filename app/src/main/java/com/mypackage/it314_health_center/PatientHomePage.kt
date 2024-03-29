package com.mypackage.it314_health_center

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.mypackage.it314_health_center.databinding.ActivityMainBinding
import com.mypackage.it314_health_center.patient_side.ActivitySettings
import com.mypackage.it314_health_center.startups.Login
import com.mypackage.it314_health_center.startups.Testing.LatencyCheck

class PatientHomePage : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        LatencyCheck.checkConcurrentRequests()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        supportActionBar?.setTitle(R.string.app_title)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_profile,
                R.id.nav_history,
                R.id.nav_settings,
                R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.menu.findItem(R.id.nav_logout).setOnMenuItemClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, Login::class.java))
            finish()
            return@setOnMenuItemClickListener true
        }

        navView.menu.findItem(R.id.nav_settings).setOnMenuItemClickListener {
            startActivity(Intent(this, ActivitySettings::class.java))
            navController.navigate(R.id.nav_home)
            return@setOnMenuItemClickListener true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.notifications) {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}