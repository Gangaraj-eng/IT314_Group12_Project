package com.mypackage.it314_health_center

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.WindowCompat
import com.google.android.material.card.MaterialCardView
import java.io.File

class ActivityOrderMedicines : AppCompatActivity() {
    private lateinit var takeAPicture: MaterialCardView
    private lateinit var uploadImage: MaterialCardView
    private lateinit var uploadPDF: MaterialCardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_medicine)

        window.statusBarColor = Color.WHITE
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
            true


        takeAPicture = findViewById(R.id.capture_picture)
        uploadImage = findViewById(R.id.upload_image)
        uploadPDF = findViewById(R.id.uploadpdf)

        takeAPicture.setOnClickListener {
            val filename: String = "photo"
            val storagedir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            try {
                val imageFile = File.createTempFile(filename, ".jpg", storagedir)
//                current_photo_path = imageFile.absolutePath
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val imageUri = FileProvider.getUriForFile(
                    this,
                    applicationContext.packageName + ".provider",
                    imageFile
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                camera_launcher.launch(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        uploadImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            imageLauncher.launch(intent)
        }

        uploadPDF.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            pdfLauncher.launch(intent)
        }
        findViewById<ImageButton>(R.id.back_btn).setOnClickListener {
            finish()
        }

    }

    private var camera_launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            if (it.resultCode == Activity.RESULT_OK) {
                startActivity(Intent(this, ConfirmOrder::class.java))
            }
        }

    private var imageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val fileuri = it.data?.data!!
                startActivity(Intent(this, ConfirmOrder::class.java))
            }
        }


    private var pdfLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val fileuri = it.data?.data!!
                startActivity(Intent(this, ConfirmOrder::class.java))

            }
        }
}