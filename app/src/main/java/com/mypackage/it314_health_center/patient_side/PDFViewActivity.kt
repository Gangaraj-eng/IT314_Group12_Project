package com.mypackage.it314_health_center.patient_side

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.firebase.storage.FirebaseStorage
import com.mypackage.it314_health_center.R
import java.io.File
import java.io.FileOutputStream


class PDFViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfview)

        val pdfurl = intent.getStringExtra("pdf_url").toString()
        val ref = FirebaseStorage.getInstance().getReferenceFromUrl(pdfurl)
        ref.getBytes(Long.MAX_VALUE).addOnSuccessListener {
            Log.d("123", it.size.toString())
            val path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            var file = File.createTempFile("my_file", ".pdf", path)
            var os = FileOutputStream(file)
            os.write(it)
            os.close()
            Log.d("123", file.absolutePath)
            val target = Intent(Intent.ACTION_VIEW)
            target.setDataAndType(
                FileProvider.getUriForFile(
                    this,
                    this.applicationContext.packageName + ".provider",
                    file
                ), "application/pdf"
            )
            target.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            val intent = Intent.createChooser(target, "Open File")
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                // Instruct the user to install a PDF reader here, or something
            }
        }
    }


}