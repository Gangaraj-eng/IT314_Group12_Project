package com.mypackage.it314_health_center.adapters

import android.app.Activity
import android.app.Dialog
import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.core.Repo
import com.google.firebase.storage.FirebaseStorage
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.helpers.ImageManager
import com.mypackage.it314_health_center.helpers.dbPaths
import com.mypackage.it314_health_center.models.MedicalReport
import com.mypackage.it314_health_center.patient_side.Activtiy_ImageViewr
import com.mypackage.it314_health_center.patient_side.PDFViewActivity
import org.w3c.dom.Text
import java.io.File
import java.io.FileOutputStream

class medical_history_reports_adapter(val context: Context,val report_list:ArrayList<MedicalReport>)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.medical_report_content, parent, false)
        return ReportViewHolder(view)
    }

    override fun getItemCount(): Int {
        return report_list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
         val cholder=holder as ReportViewHolder

        cholder.rdate.text=report_list[position].uploadedOnDate
        cholder.rtime.text=report_list[position].uploadedOnTime
        cholder.reportName.text=report_list[position].reportName
        cholder.reportType.text=report_list[position].reportType
        if(holder.itemViewType== TYPE_REPORT)
        {
            cholder.reportImage.setImageResource(R.drawable.pdf_image)
            cholder.reportImage.setOnClickListener{
                val pdfurl=report_list[position].reportURL
                val dialog=Dialog(context)
                dialog.setContentView(R.layout.error_layout)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
                dialog.findViewById<TextView>(R.id.txt__verify).text="Loading.."
                openFile(pdfurl,dialog)

            }
        }
        else{
            ImageManager.loadImageIntoView(cholder.reportImage,report_list[position].reportURL)
            cholder.reportImage.setOnClickListener {
                cholder.reportImage.invalidate()
                val bitmap = cholder.reportImage.drawable.toBitmap()
                val intent = Intent(it.context, Activtiy_ImageViewr::class.java)
                intent.putExtra("Image_bitmap", bitmap)
                intent.putExtra("image_url", report_list[position].reportURL)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    it.context as Activity, holder.itemView, "fade"
                )
                it.context.startActivity(intent, options.toBundle())
            }
        }
    }

    inner class ReportViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val reportImage:ImageView=itemview.findViewById(R.id.report_image)
        val reportName:TextView=itemview.findViewById(R.id.report_name)
        val reportType:TextView=itemview.findViewById(R.id.report_type)
        val rdate:TextView=itemview.findViewById(R.id.ru_data)
        val rtime:TextView=itemview.findViewById(R.id.ru_time)
    }
    override fun getItemViewType(position: Int): Int {
         if(report_list[position].reportType==dbPaths.REPORT_IMAGE)
             return TYPE_IMAGE
        return TYPE_REPORT
    }

    companion object {
        const val TYPE_IMAGE = 1
        const val TYPE_REPORT = 2
    }

    fun openFile(pdfurl:String,dialog: Dialog)
    {
        val ref= FirebaseStorage.getInstance().getReferenceFromUrl(pdfurl)
        ref.getBytes(Long.MAX_VALUE).addOnSuccessListener {
            Log.d("123",it.size.toString())
            val path = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            var file = File.createTempFile("my_file",".pdf", path)
            var os = FileOutputStream(file)
            os.write(it)
            os.close()
            Log.d("123",file.absolutePath)
            val target = Intent(Intent.ACTION_VIEW)
            target.setDataAndType(FileProvider.getUriForFile(context,context.applicationContext.packageName+".provider",file), "application/pdf")
            target.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            val intent = Intent.createChooser(target, "Open File")
            try {
                context.startActivity(intent)
                dialog.dismiss()
            } catch (e: ActivityNotFoundException) {
                // Instruct the user to install a PDF reader here, or something
                dialog.dismiss()
            }
        }
    }
}