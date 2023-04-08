package com.mypackage.it314_health_center.patient_side

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.transition.Fade
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mypackage.it314_health_center.R

class Activtiy_ImageViewr : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activtiy_image_viewr)
        hideSystemBars()
        // setting transition
        val fade = Fade()
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        window.enterTransition = fade
        window.exitTransition = fade

        // We need to pass imageUrl to this from intent
        val image_bitmap = intent.extras?.get("Image_bitmap") as Bitmap
        val url = intent.extras?.get("image_url") as String
        val zoomable_img_view = findViewById<ImageView>(R.id.zoomable_image_view)
        zoomable_img_view.setImageBitmap(image_bitmap)
        Handler().postDelayed({
            val storageReference = Firebase.storage.getReferenceFromUrl(url)
            storageReference.downloadUrl
                .addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    Glide.with(zoomable_img_view.context)
                        .load(downloadUrl).into(zoomable_img_view)
                }

        }, 1500)
        // close when back button is pressed
        findViewById<ImageButton>(R.id.back_btn).setOnClickListener {
            onBackPressed() // or finish()
        }

    }

    private fun hideSystemBars() {
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT
    }
}